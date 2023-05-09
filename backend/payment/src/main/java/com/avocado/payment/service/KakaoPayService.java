package com.avocado.payment.service;

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
import com.avocado.payment.exception.ErrorCode;
import com.avocado.payment.exception.InvalidValueException;
import com.avocado.payment.exception.KakaoPayException;
import com.avocado.payment.exception.NoInventoryException;
import com.avocado.payment.repository.*;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KakaoPayService {
    @Value("${kakao-pay.host}")
    private String host;
    @Value("${kakao-pay.url.api.ready}")
    private String apiReadyUrl;
    @Value("${kakao-pay.url.api.approve}")
    private String apiApproveUrl;
    @Value("${kakao-pay.cid}")
    private String cid;

    @Value("${kakao-pay.url.handle.host}")
    private String handleHost;
    @Value("${kakao-pay.url.handle.approve}")
    private String handleApprovalUrl;
    @Value("${kakao-pay.url.handle.cancel}")
    private String handleCancelUrl;
    @Value("${kakao-pay.url.handle.fail}")
    private String handleFailUrl;

    @PersistenceContext
    private final EntityManager em;
    private final RestTemplate restTemplate;

    private final String LOCK_NAME = "PAY_LOCK";
    private final RedissonClient redissonClient;

    private final MerchandiseRepository merchandiseRepository;
    private final PurchasingRepository purchasingRepository;
    private final ConsumerRepository consumerRepository;
    private final PurchaseRepository purchaseRepository;

    private final UUIDUtil uuidUtil;
    private final KakaoPayUtil kakaoPayUtil;

    @Transactional
    public KakaoPayRedirectUrlResp ready(String consumerId, ReadyForPaymentReq paymentReq) {
        List<PurchaseMerchandiseReq> merchandiseReqs = paymentReq.getMerchandises();

        // 요청한 소비자가 존재하는지 확인
        Optional<Consumer> optionalConsumer = consumerRepository.findById(uuidUtil.joinByHyphen(consumerId));
        if (optionalConsumer.isEmpty())
            throw new InvalidValueException(ErrorCode.NO_MEMBER);

        // 구매할 상품 검색
        //  1. 구매할 상품들의 ID 취합
        List<Long> merchandiseIds = new ArrayList<>();
        merchandiseReqs.forEach((merchandiseReq) -> merchandiseIds.add(merchandiseReq.getMerchandise_id()));

        //  2. 상품 검색
        List<Merchandise> merchandiseList = merchandiseRepository.findByIdIn(merchandiseIds);

        //  3. 모두 유효한 상품인지 확인
        if (merchandiseList.size() != merchandiseReqs.size())
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

        // 데이터 취합하여 body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("cid", cid);
        body.add("partner_order_id", strPurchaseId);
        body.add("partner_user_id", consumerId);
        body.add("item_name", name);
        body.add("quantity", String.valueOf(quantity));
        body.add("total_amount", String.valueOf(paymentReq.getTotal_price()));
        body.add("tax_free_amount", "0");
        body.add("approval_url", handleHost + handleApprovalUrl + strPurchaseId);
        body.add("cancel_url", handleHost + handleCancelUrl + strPurchaseId);
        body.add("fail_url", handleHost + handleFailUrl + strPurchaseId);

        // body, headers 취합
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, kakaoPayUtil.getKakaoPayHeaders());

        // API 요청
        ResponseEntity<KakaoPayReadyResp> response = restTemplate.postForEntity(
                host + apiReadyUrl,
                entity,
                KakaoPayReadyResp.class
        );

        // 오류 발생 시 예외 던지기
        if (response.getStatusCodeValue() != 200)
            throw new KakaoPayException(ErrorCode.READY_ERROR);
        KakaoPayReadyResp kakaoPayReadyResp = response.getBody();

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
                            .size((System.currentTimeMillis() & 1) == 1 ? "L" : "XL")  // 사이즈는 제공하지 않으므로 랜덤 설정
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
                .build();
        purchasingRepository.save(purchasing);

        // Response 생성 및 반환
        KakaoPayRedirectUrlResp kakaoPayRedirectUrlResp = KakaoPayRedirectUrlResp.builder()
                .next_redirect_mobile_url(kakaoPayReadyResp.getNext_redirect_mobile_url())
                .next_redirect_pc_url(kakaoPayReadyResp.getNext_redirect_pc_url())
                .build();
        return kakaoPayRedirectUrlResp;
    }

    @Transactional
    public void approve(String purchasingId, String pgToken) {
        // 구매 대기 내역 조회
        Optional<Purchasing> optionalPurchasing = purchasingRepository.findById(purchasingId);
        if (optionalPurchasing.isEmpty())
            throw new InvalidValueException(ErrorCode.NO_PURCHASING);
        Purchasing purchasing = optionalPurchasing.get();
        String consumerId = purchasing.getConsumer_id();

        // 분산 락으로 동시성 처리
        RLock lock = redissonClient.getLock(LOCK_NAME);

        try {
            // Lock 획득 시도
            if (!(lock.tryLock(1, 3, TimeUnit.SECONDS)))
                throw new RuntimeException("Failed to get lock");

            // 재고 확인
            if (!isEnoughInventory(purchasing))
                throw new NoInventoryException(ErrorCode.NO_INVENTORY);

            // body 생성
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("cid", cid);
            body.add("tid", purchasing.getTid());
            body.add("partner_order_id", purchasingId);
            body.add("partner_user_id", consumerId);
            body.add("pg_token", pgToken);
            body.add("total_amount", String.valueOf(purchasing.getTotal_price()));

            // headers와 취합하여 entity 생성
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, kakaoPayUtil.getKakaoPayHeaders());

            // 카카오페이에 승인 요청
            ResponseEntity<KakaoPayApproveResp> response = restTemplate.postForEntity(
                    host + apiApproveUrl,
                    httpEntity,
                    KakaoPayApproveResp.class
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

            System.out.println(response.getBody());
        } catch (InterruptedException e) {
            throw new RuntimeException("Lock Interrupted Exception");
        } finally {
            // Lock 해제
            lock.unlock();
        }
    }

    @Transactional
    public void cancel(String purchasingId) {

    }

    @Transactional
    public void fail(String purchasingId) {

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
        for (PurchasingMerchandise merchandise : merchandises)
            bulkUpdate += " when " + merchandise.getMerchandise_id()
                    + " then " + merchandise.getQuantity();
        bulkUpdate += " end";
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
