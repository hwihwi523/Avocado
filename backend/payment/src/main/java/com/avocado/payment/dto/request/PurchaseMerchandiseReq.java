package com.avocado.payment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PurchaseMerchandiseReq {
    private final Long merchandise_id;
    private final Integer quantity;
    private final Long price;

    @Builder
    public PurchaseMerchandiseReq(Long merchandise_id, Integer quantity, Long price) {
        this.merchandise_id = merchandise_id;
        this.quantity = quantity;
        this.price = price;
    }
}
