package com.avocado.payment.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadyForPaymentReq {
    private final Long total_price;
    private final String success_url;
    private final String fail_url;
    private final List<PurchaseMerchandiseReq> merchandises;

    @Builder
    public ReadyForPaymentReq(Long total_price, String success_url, String fail_url, List<PurchaseMerchandiseReq> merchandises) {
        this.total_price = total_price;
        this.success_url = success_url;
        this.fail_url = fail_url;
        this.merchandises = merchandises;
    }
}
