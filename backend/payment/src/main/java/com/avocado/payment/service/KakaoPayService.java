package com.avocado.payment.service;

import com.avocado.payment.annotation.DistributedLock;
import com.avocado.payment.config.KakaoPayUtil;
import com.avocado.payment.config.UUIDUtil;
import com.avocado.payment.dto.kakaopay.KakaoPayApproveResp;
import com.avocado.payment.dto.kakaopay.KakaoPayReadyResp;
import com.avocado.payment.dto.request.PurchaseMerchandiseReq;
import com.avocado.payment.dto.request.ReadyForPaymentReq;
import com.avocado.payment.dto.response.KakaoPayRedirectUrlResp;
import com.avocado.payment.entity.*;
import com.avocado.payment.entity.redis.Purchasing;
import com.avocado.payment.entity.redis.PurchasingMerchandise;
import com.avocado.payment.enums.DistributedLockName;
import com.avocado.payment.exception.*;
import com.avocado.payment.kafka.service.KafkaProducer;
import com.avocado.payment.repository.ConsumerRepository;
import com.avocado.payment.repository.MerchandiseRepository;
import com.avocado.payment.repository.PurchaseRepository;
import com.avocado.payment.repository.PurchasingRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KakaoPayService {
    @PersistenceContext
    private final EntityManager em;

    private final MerchandiseRepository merchandiseRepository;
    private final PurchasingRepository purchasingRepository;
    private final ConsumerRepository consumerRepository;
    private final PurchaseRepository purchaseRepository;
    private final KafkaProducer kafkaProducer;

    private final UUIDUtil uuidUtil;
    private final KakaoPayUtil kakaoPayUtil;

    /**
     * 결제 준비 요청. 구매 요청 정보를 저장하고, 카카오페이로 거래 정보를 보내 거래 시작을 알리는 메서드
     * @param consumerUuid : 구매자 ID
     * @param paymentReq : 구매 정보
     * @return : 카카오페이 결제 URL
     */
    @Transactional
    public KakaoPayRedirectUrlResp ready(UUID consumerUuid, ReadyForPaymentReq paymentReq) {
        // 소비자 ID in String
        String consumerId = uuidUtil.removeHyphen(consumerUuid);

        List<PurchaseMerchandiseReq> merchandiseReqs = paymentReq.getMerchandises();

        // 요청한 소비자가 존재하는지 확인
        consumerRepository.findById(consumerUuid)
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NO_MEMBER));

        // 구매할 상품 검색
        //  1. 구매할 상품들의 ID 취합
        List<Long> merchandiseIds = new ArrayList<>();
        merchandiseReqs.forEach((merchandiseReq) -> merchandiseIds.add(merchandiseReq.getMerchandise_id()));

        //  2. 상품 검색
        List<Merchandise> merchandiseList = merchandiseRepository.findByIdIn(merchandiseIds);

        //  3. 모두 유효한 상품인지 확인
        Set<Long> requestMerchandiseSet = new HashSet<>();  // 구매 요청한 상품의 품목 수 구하기
        for (PurchaseMerchandiseReq merchandiseReq : merchandiseReqs)
            requestMerchandiseSet.add(merchandiseReq.getMerchandise_id());
        if (merchandiseList.size() != requestMerchandiseSet.size())  // 요청한 상품의 품목 수와 조회한 품목 수가 다르다면 유효 X
            throw new InvalidValueException(ErrorCode.NO_MERCHANDISE);

        // 결제할 품목의 개수에 따라 요청 데이터 가공
        UUID purchaseId = UUID.randomUUID();  // 구매내역 ID
        String strPurchaseId = uuidUtil.removeHyphen(purchaseId);  // 하이픈 제거한 구매내역 ID
        int quantity;
        String name;

        if (merchandiseList.size() == 1) {  // 단품 결제 시 수량, 상품명 그대로 표기
            quantity = merchandiseReqs.get(0).getQuantity();
            name = merchandiseList.get(0).getName();
        } else {  // 다량 품목 결제 시 수량은 1, 상품명은 '${name} 외 N건' 으로 표기
            quantity = 1;
            name = merchandiseList.get(0).getName() +
                    " 외 " + (merchandiseList.size() - 1) + "건";
        }

        // API 요청
        ResponseEntity<KakaoPayReadyResp> response = kakaoPayUtil.getReady(
                strPurchaseId, consumerId, name, quantity, paymentReq.getTotal_price()
        );

        // 오류 발생 시 예외 던지기
        if (response.getStatusCodeValue() != 200)
            throw new KakaoPayException(ErrorCode.READY_ERROR);
        KakaoPayReadyResp kakaoPayReadyResp = response.getBody();

        // 상품별 판매자 ID
        Map<Long, UUID> providerIdMap = new HashMap<>();
        for (Merchandise merchandise : merchandiseList)
            providerIdMap.put(merchandise.getId(), merchandise.getStore().getProviderId());

        // 구매 상품 리스트 취합
        List<PurchasingMerchandise> purchasingMerchandises = new ArrayList<>();
        for (PurchaseMerchandiseReq merchandiseReq : merchandiseReqs) {
            String providerId = uuidUtil.removeHyphen(providerIdMap.get(merchandiseReq.getMerchandise_id()));
            purchasingMerchandises.add(
                    PurchasingMerchandise.builder()
                            .merchandise_id(merchandiseReq.getMerchandise_id())
                            .price(merchandiseReq.getPrice())
                            .provider_id(providerId)
                            .quantity(merchandiseReq.getQuantity())
                            .size(merchandiseReq.getSize())
                            .build()
            );
        }

        // 구매 대기 내역 등록 (Redis)
        Purchasing purchasing = Purchasing.builder()
                .id(strPurchaseId)
                .tid(kakaoPayReadyResp.getTid())
                .consumer_id(consumerId)
                .total_price(paymentReq.getTotal_price())
                .merchandises(purchasingMerchandises)
                .success_url(paymentReq.getSuccess_url())
                .fail_url(paymentReq.getFail_url())
                .build();
        purchasingRepository.save(purchasing);

        // Response 생성 및 반환
        KakaoPayRedirectUrlResp kakaoPayRedirectUrlResp = KakaoPayRedirectUrlResp.builder()
                .next_redirect_mobile_url(kakaoPayReadyResp.getNext_redirect_mobile_url())
                .next_redirect_pc_url(kakaoPayReadyResp.getNext_redirect_pc_url())
                .build();
        return kakaoPayRedirectUrlResp;
    }

    /**
     * 결제 승인 요청. 구매 대기 정보를 받아 결제 승인을 처리하는 메서드.
     * @param purchasingId : 구매 대기 정보 ID
     * @param pgToken : 카카오페이로부터 받은 결제 토큰
     */
    @Transactional
    public String approve(String purchasingId, String pgToken) {

        // 구매 대기 내역 조회
        Optional<Purchasing> optionalPurchasing = purchasingRepository.findById(purchasingId);
        if (optionalPurchasing.isEmpty())
            throw new InvalidValueException(ErrorCode.NO_PURCHASING);
        Purchasing purchasing = optionalPurchasing.get();

        // 재고를 파악한 뒤 카카오페이 서버로 승인 요청을 보내 결제 완료 처리
        completeKakaoPay(purchasing, purchasing.getConsumer_id(), pgToken);

        return purchasing.getSuccess_url();
    }

    /**
     * Redis 저장하던 구매 대기 정보 삭제
     * @param purchasingId : 구매 대기 내역 ID
     */
    @Transactional
    public String cancel(String purchasingId) {
        Optional<Purchasing> optionalPurchasing = purchasingRepository.findById(purchasingId);
        if (optionalPurchasing.isPresent()) {
            Purchasing purchasing = optionalPurchasing.get();
            String cancelUrl = purchasing.getFail_url();
            purchasingRepository.delete(purchasing);
            return cancelUrl;
        }
        throw new KakaoPayException(ErrorCode.CANCEL_ERROR);
    }

    /**
     * Redis 저장하던 구매 대기 정보 삭제하고 결제 실패 예외 던지기
     * @param purchasingId : 구매 대기 내역 ID
     */
    @Transactional
    public String fail(String purchasingId) {
        Optional<Purchasing> optionalPurchasing = purchasingRepository.findById(purchasingId);
        if (optionalPurchasing.isPresent()) {
            Purchasing purchasing = optionalPurchasing.get();
            purchasingRepository.delete(purchasing);
            return purchasing.getFail_url();
        }
        throw new KakaoPayException(ErrorCode.APPROVE_ERROR);
    }

    /**
     * 카카오페이 서버로 결제 승인 요청을 보낸 뒤 결제를 마무리하는 메서드
     * (분산 락 적용하여 재고 동시성 처리)
     * @param purchasing : 구매 대기 정보
     * @param consumerId : 구매자 ID
     * @param pgToken : 카카오페이로부터 전달받은 결제 토큰
     */
    @DistributedLock(key = DistributedLockName.PAY)
    private void completeKakaoPay(Purchasing purchasing, String consumerId, String pgToken) {

        String purchasingId = purchasing.getId();

        // 재고 확인
        if (!isEnoughInventory(purchasing))
            throw new NoInventoryException(ErrorCode.NO_INVENTORY);

        // 카카오페이에 승인 요청
        ResponseEntity<KakaoPayApproveResp> response = kakaoPayUtil.getApprove(
                purchasing.getTid(), purchasingId, consumerId, pgToken, purchasing.getTotal_price()
        );

        // 정상 응답이 아니라면 예외 던지기
        if (response.getStatusCodeValue() != 200)
            throw new KakaoPayException(ErrorCode.APPROVE_ERROR);
        KakaoPayApproveResp approveResp = response.getBody();

        // 구매 완료 내역 업데이트
        Purchase purchase = Purchase.builder()
                .id(uuidUtil.joinByHyphen(purchasingId))
                .consumerId(uuidUtil.joinByHyphen(consumerId))
                .tid(purchasing.getTid())
                .totalPrice(purchasing.getTotal_price())
                .createdAt(LocalDateTime.parse(approveResp.getApproved_at()))
                .build();
        purchaseRepository.save(purchase);

        // 구매 완료된 상품 테이블 업데이트
        bulkInsert(purchasing);

        // Redis 데이터 삭제
        purchasingRepository.delete(purchasing);

        // 재고 감소
        bulkUpdate(purchasing.getMerchandises());

        // produce to kafka
        kafkaProducer.sendPurchaseHistory(purchasing);
    }

    /**
     * 테스트 결제
     * @param consumerUuid : 구매자 ID
     * @param paymentReq : 구매 정보
     */
    @Transactional
    public void testPay(UUID consumerUuid, ReadyForPaymentReq paymentReq) {
        // 소비자 ID in String
        String consumerId = uuidUtil.removeHyphen(consumerUuid);

        List<PurchaseMerchandiseReq> merchandiseReqs = paymentReq.getMerchandises();

        // 요청한 소비자가 존재하는지 확인
        consumerRepository.findById(uuidUtil.joinByHyphen(consumerId))
                .orElseThrow(() -> new InvalidValueException(ErrorCode.NO_MEMBER));

        // 구매할 상품 검색
        //  1. 구매할 상품들의 ID 취합
        List<Long> merchandiseIds = new ArrayList<>();
        merchandiseReqs.forEach((merchandiseReq) -> merchandiseIds.add(merchandiseReq.getMerchandise_id()));

        //  2. 상품 검색
        List<Merchandise> merchandiseList = merchandiseRepository.findByIdIn(merchandiseIds);

        //  3. 모두 유효한 상품인지 확인
        Set<Long> requestMerchandiseSet = new HashSet<>();  // 구매 요청한 상품의 품목 수 구하기
        for (PurchaseMerchandiseReq merchandiseReq : merchandiseReqs)
            requestMerchandiseSet.add(merchandiseReq.getMerchandise_id());
        if (merchandiseList.size() != requestMerchandiseSet.size())  // 요청한 상품의 품목 수와 조회한 품목 수가 다르다면 유효 X
            throw new InvalidValueException(ErrorCode.NO_MERCHANDISE);

        // 결제할 품목의 개수에 따라 요청 데이터 가공
        UUID purchaseId = UUID.randomUUID();  // 구매내역 ID
        String strPurchaseId = uuidUtil.removeHyphen(purchaseId);  // 하이픈 제거한 구매내역 ID

        // 판매자 ID
        Map<Long, UUID> providerIdMap = new HashMap<>();
        for (Merchandise merchandise : merchandiseList)
            providerIdMap.put(merchandise.getId(), merchandise.getStore().getProviderId());

        // 구매 상품 리스트 취합
        List<PurchasingMerchandise> purchasingMerchandises = new ArrayList<>();
        for (PurchaseMerchandiseReq merchandiseReq : merchandiseReqs) {
            String providerId = uuidUtil.removeHyphen(providerIdMap.get(merchandiseReq.getMerchandise_id()));
            purchasingMerchandises.add(
                    PurchasingMerchandise.builder()
                            .merchandise_id(merchandiseReq.getMerchandise_id())
                            .price(merchandiseReq.getPrice())
                            .provider_id(providerId)
                            .quantity(merchandiseReq.getQuantity())
                            .size(merchandiseReq.getSize())
                            .build()
            );
        }

        // 구매 대기 내역 등록 (Redis)
        Purchasing purchasing = Purchasing.builder()
                .id(strPurchaseId)
                .consumer_id(consumerId)
                .total_price(paymentReq.getTotal_price())
                .merchandises(purchasingMerchandises)
                .build();
        purchasingRepository.save(purchasing);

        // 구매내역 등록 및 재고 감소 등 실거래 완료 처리
        testApprove(purchasing);
    }
    @DistributedLock(key = DistributedLockName.PAY)
    public void testApprove(Purchasing purchasing) {
        String purchasingId = purchasing.getId();
        String consumerId = purchasing.getConsumer_id();

        // 재고 확인
        if (!isEnoughInventory(purchasing))
            throw new NoInventoryException(ErrorCode.NO_INVENTORY);

        // 구매 완료 내역 업데이트
        Purchase purchase = Purchase.builder()
                .id(uuidUtil.joinByHyphen(purchasingId))
                .consumerId(uuidUtil.joinByHyphen(consumerId))
                .tid(purchasing.getTid())
                .totalPrice(purchasing.getTotal_price())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
        purchaseRepository.save(purchase);

        // 구매 완료된 상품 테이블 업데이트
        bulkInsert(purchasing);

        // Redis 데이터 삭제
        purchasingRepository.delete(purchasing);

        // 재고 감소
        bulkUpdate(purchasing.getMerchandises());
    }

    /**
     * 구매 완료 상품 bulk insert
     */
    @Transactional
    public void bulkInsert(Purchasing purchasing) {
        List<PurchasingMerchandise> merchandises = purchasing.getMerchandises();

        // 상품 ID, 구매대기내역 ID 모두 내부 데이터를 사용하기 때문에 sql injection 문제 없을 듯 (아마)
        String bulkQuery = "INSERT INTO purchased_merchandise (merchandise_id, purchase_id, provider_id, price, quantity, size) " +
                "VALUES " + getValue(purchasing.getId(), merchandises.get(0));
        for (int i = 1; i < merchandises.size(); i++)
            bulkQuery += ", " + getValue(purchasing.getId(), merchandises.get(i));
        em.createNativeQuery(bulkQuery).executeUpdate();
    }
    private String getValue(String purchaseId, PurchasingMerchandise merchandise) {
        return "("+ merchandise.getMerchandise_id()
                + ", UNHEX(\"" + purchaseId + "\")"
                + ", UNHEX(\"" + merchandise.getProvider_id() + "\")"
                + ", " + merchandise.getPrice()
                + ", " + merchandise.getQuantity()
                + ", \"" + merchandise.getSize() + "\""
                +")";
    }

    /**
     * 재고 bulk update
     */
    @Transactional
    public void bulkUpdate(List<PurchasingMerchandise> merchandises) {
        String bulkUpdate = "update merchandise m set m.inventory = m.inventory - case m.id";
        String whereClause = " where m.id in (null";
        for (PurchasingMerchandise merchandise : merchandises) {
            bulkUpdate += " when " + merchandise.getMerchandise_id()
                    + " then " + merchandise.getQuantity();
            whereClause += ", " + merchandise.getMerchandise_id();
        }
        whereClause += ")";
        bulkUpdate += " else 0 end" + whereClause;
        // 업데이트
        em.createNativeQuery(bulkUpdate).executeUpdate();
    }

    /**
     * 결제 승인 직전 재고가 충분한지 확인하는 메서드
     * @param purchasing : 구매 대기중인 내역
     * @return : 재고의 충분 여부
     */
    @Transactional(readOnly = true)
    boolean isEnoughInventory(Purchasing purchasing) {
        // 상품 ID 취합, Map<상품 ID, 구매수량> 생성
        List<Long> merchandiseIds = new ArrayList<>();
        Map<Long, Integer> quantityOf = new HashMap<>();
        for (PurchasingMerchandise merchandise : purchasing.getMerchandises()) {
            merchandiseIds.add(merchandise.getMerchandise_id());
            quantityOf.put(merchandise.getMerchandise_id(), merchandise.getQuantity());
        }

        // 상품 검색 및 재고 확인
        List<Merchandise> merchandises = merchandiseRepository.findByIdIn(merchandiseIds);
        for (Merchandise merchandise : merchandises) {
            // 구매수량이 재고보다 많으면 False 반환
            if (merchandise.getInventory() < quantityOf.get(merchandise.getId()))
                return false;
        }

        return true;
    }
}
