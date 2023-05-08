package com.avocado.payment.service;

import com.avocado.payment.config.KakaoPayUtil;
import com.avocado.payment.config.UUIDUtil;
import com.avocado.payment.dto.kakaopay.KakaoPayReadyResp;
import com.avocado.payment.dto.request.PurchaseMerchandiseReq;
import com.avocado.payment.dto.request.ReadyForPaymentReq;
import com.avocado.payment.dto.response.KakaoPayRedirectUrlResp;
import com.avocado.payment.entity.Consumer;
import com.avocado.payment.entity.Merchandise;
import com.avocado.payment.entity.Purchasing;
import com.avocado.payment.exception.ErrorCode;
import com.avocado.payment.exception.InvalidValueException;
import com.avocado.payment.exception.KakaoPayException;
import com.avocado.payment.repository.ConsumerRepository;
import com.avocado.payment.repository.MerchandiseRepository;
import com.avocado.payment.repository.PurchasingRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoPayService {
    @Value("${kakao-pay.host}")
    private String host;
    @Value("${kakao-pay.ready-url}")
    private String readyUrl;
    @Value("${kakao-pay.cid}")
    private String cid;
    @Value("${kakao-pay.approval-url}")
    private String approvalUrl;
    @Value("${kakao-pay.cancel-url}")
    private String cancelUrl;
    @Value("${kakao-pay.fail-url}")
    private String failUrl;

    @PersistenceContext
    private final EntityManager em;
    private final RestTemplate restTemplate;

    private final MerchandiseRepository merchandiseRepository;
    private final PurchasingRepository purchasingRepository;
    private final ConsumerRepository consumerRepository;
    private final UUIDUtil uuidUtil;
    private final KakaoPayUtil kakaoPayUtil;

    @Transactional
    public KakaoPayRedirectUrlResp ready(ReadyForPaymentReq paymentReq) {
        List<PurchaseMerchandiseReq> merchandiseReqs = paymentReq.getMerchandises();

        // 요청한 소비자가 존재하는지 확인
        Optional<Consumer> optionalConsumer = consumerRepository.findById(uuidUtil.joinByHyphen(paymentReq.getUser_id()));
        if (optionalConsumer.isEmpty())
            throw new InvalidValueException(ErrorCode.NO_MEMBER);
        Consumer consumer = optionalConsumer.get();

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
        body.add("partner_user_id", paymentReq.getUser_id());
        body.add("item_name", name);
        body.add("quantity", String.valueOf(quantity));
        body.add("total_amount", String.valueOf(paymentReq.getTotal_price()));
        body.add("tax_free_amount", "0");
        body.add("approval_url", approvalUrl + strPurchaseId);
        body.add("cancel_url", cancelUrl + strPurchaseId);
        body.add("fail_url", failUrl + strPurchaseId);

        // body, headers 취합
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, kakaoPayUtil.getKakaoPayHeaders());

        // API 요청
        ResponseEntity<KakaoPayReadyResp> response = restTemplate.postForEntity(
                host + readyUrl,
                entity,
                KakaoPayReadyResp.class
        );

        // 오류 발생 시 예외 던지기
        if (response.getStatusCodeValue() != 200)
            throw new KakaoPayException(ErrorCode.UNKNOWN_ERROR);
        KakaoPayReadyResp kakaoPayReadyResp = response.getBody();

        // 구매 대기 내역 등록
        Purchasing purchasing = Purchasing.builder()
                .id(purchaseId)
                .tid(kakaoPayReadyResp.getTid())
                .consumer(consumer)
                .totalPrice(paymentReq.getTotal_price())
                .build();
        purchasingRepository.save(purchasing);

        // 구매 대기 상품 등록 (Native Query Bulk)
        // 상품 ID, 구매대기내역 ID 모두 내부 데이터를 사용하기 때문에 sql injection 문제 없을 듯 (아마)
        String bulkQuery = "INSERT INTO purchasing_merchandise (merchandise_id, purchasing_id) " +
                "VALUES (" + merchandiseIds.get(0) + ", UNHEX(\"" + strPurchaseId + "\"))";
        for (int i = 1; i < merchandiseIds.size(); i++)
            bulkQuery += ", (" + merchandiseIds.get(i) + ", UNHEX(\"" + strPurchaseId + "\"))";
        em.createNativeQuery(bulkQuery).executeUpdate();

        // Response 생성 및 반환
        KakaoPayRedirectUrlResp kakaoPayRedirectUrlResp = KakaoPayRedirectUrlResp.builder()
                .next_redirect_mobile_url(kakaoPayReadyResp.getNext_redirect_mobile_url())
                .next_redirect_pc_url(kakaoPayReadyResp.getNext_redirect_pc_url())
                .build();
        return kakaoPayRedirectUrlResp;
    }
}
