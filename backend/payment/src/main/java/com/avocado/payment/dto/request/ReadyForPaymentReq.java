package com.avocado.payment.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadyForPaymentReq {
    private final String user_id;  // 로그인 구현 시 삭제 예정
    private final Long total_price;
    private final List<PurchaseMerchandiseReq> merchandises;

    @Builder
    public ReadyForPaymentReq(String user_id, Long total_price, List<PurchaseMerchandiseReq> merchandises) {
        this.user_id = user_id;
        this.total_price = total_price;
        this.merchandises = merchandises;
    }
}
