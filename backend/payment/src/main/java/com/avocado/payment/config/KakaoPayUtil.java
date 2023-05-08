package com.avocado.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KakaoPayUtil {
    @Value("${kakao-pay.admin-key}")
    private String kakaoAdminKey;
    private HttpHeaders kakaoPayHeaders;

    private KakaoPayUtil() {}
    @PostConstruct
    private void init() {
        // 헤더 설정
        kakaoPayHeaders = new HttpHeaders();
        kakaoPayHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        kakaoPayHeaders.set("Authorization", "KakaoAK " + kakaoAdminKey);
    }

    public HttpHeaders getKakaoPayHeaders() {
        return kakaoPayHeaders;
    }
}
