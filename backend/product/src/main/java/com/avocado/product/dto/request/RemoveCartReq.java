package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RemoveCartReq {
    private String user_id;
    private Long cart_id;

    @Builder
    public RemoveCartReq(String user_id, Long cart_id) {
        this.user_id = user_id;
        this.cart_id = cart_id;
    }
}
