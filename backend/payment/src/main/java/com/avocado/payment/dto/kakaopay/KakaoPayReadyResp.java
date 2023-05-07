package com.avocado.payment.dto.kakaopay;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoPayReadyResp {
    private String tid;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;

    @Builder
    public KakaoPayReadyResp(String tid, String next_redirect_mobile_url, String next_redirect_pc_url) {
        this.tid = tid;
        this.next_redirect_mobile_url = next_redirect_mobile_url;
        this.next_redirect_pc_url = next_redirect_pc_url;
    }
}
