package com.avocado.payment.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadyForPaymentReq {
    private final Long total_price;
    private final List<PurchaseMerchandiseReq> merchandises;

    @Builder
    public ReadyForPaymentReq(Long total_price, List<PurchaseMerchandiseReq> merchandises) {
        this.total_price = total_price;
        this.merchandises = merchandises;
    }
}
