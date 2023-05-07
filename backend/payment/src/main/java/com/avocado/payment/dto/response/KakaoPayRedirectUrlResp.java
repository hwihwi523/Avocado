package com.avocado.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoPayRedirectUrlResp {
    private final String next_redirect_mobile_url;
    private final String next_redirect_pc_url;

    @Builder
    public KakaoPayRedirectUrlResp(String next_redirect_mobile_url, String next_redirect_pc_url) {
        this.next_redirect_mobile_url = next_redirect_mobile_url;
        this.next_redirect_pc_url = next_redirect_pc_url;
    }
}
