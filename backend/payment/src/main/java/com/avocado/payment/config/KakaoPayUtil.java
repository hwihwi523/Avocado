package com.avocado.payment.config;

import com.avocado.payment.dto.kakaopay.KakaoPayApproveResp;
import com.avocado.payment.dto.kakaopay.KakaoPayReadyResp;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class KakaoPayUtil {
    private final RestTemplate restTemplate;
    @Value("${kakao-pay.host}")
    private String apiHost;
    @Value("${kakao-pay.url.api.ready}")
    private String apiReadyUrl;
    @Value("${kakao-pay.url.api.approve}")
    private String apiApproveUrl;

    @Value("${kakao-pay.url.handle.host}")
    private String handleHost;
    @Value("${kakao-pay.url.handle.approve}")
    private String handleApprovalUrl;
    @Value("${kakao-pay.url.handle.cancel}")
    private String handleCancelUrl;
    @Value("${kakao-pay.url.handle.fail}")
    private String handleFailUrl;

    @Value("${kakao-pay.cid}")
    private String cid;

    // 헤더 돌려쓰기
    @Value("${kakao-pay.admin-key}")
    private String kakaoAdminKey;
    private HttpHeaders kakaoPayHeaders;
    @PostConstruct
    private void init() {
        // 헤더 설정
        kakaoPayHeaders = new HttpHeaders();
        kakaoPayHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        kakaoPayHeaders.set("Authorization", "KakaoAK " + kakaoAdminKey);
    }

    public ResponseEntity<KakaoPayReadyResp> getReady(String purchaseId, String consumerId, String mdName,
                                                      int quantity, long totalPrice) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("cid", cid);
        body.add("partner_order_id", purchaseId);
        body.add("partner_user_id", consumerId);
        body.add("item_name", mdName);
        body.add("quantity", String.valueOf(quantity));
        body.add("total_amount", String.valueOf(totalPrice));
        body.add("tax_free_amount", "0");
        body.add("approval_url", handleHost + handleApprovalUrl + purchaseId);
        body.add("cancel_url", handleHost + handleCancelUrl + purchaseId);
        body.add("fail_url", handleHost + handleFailUrl + purchaseId);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, kakaoPayHeaders);

        return restTemplate.postForEntity(
                apiHost + apiReadyUrl,
                httpEntity,
                KakaoPayReadyResp.class
        );
    }

    public ResponseEntity<KakaoPayApproveResp> getApprove(String tid, String purchasingId, String consumerId,
                                                          String pgToken, long totalPrice) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("cid", cid);
        body.add("tid", tid);
        body.add("partner_order_id", purchasingId);
        body.add("partner_user_id", consumerId);
        body.add("pg_token", pgToken);
        body.add("total_amount", String.valueOf(totalPrice));

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, kakaoPayHeaders);

        return restTemplate.postForEntity(
                apiHost + apiApproveUrl,
                httpEntity,
                KakaoPayApproveResp.class
        );
    }
}
