package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddCartReq {
    private final String user_id;
    private final Long merchandise_id;

    @Builder
    public AddCartReq(String user_id, Long merchandise_id) {
        this.user_id = user_id;
        this.merchandise_id = merchandise_id;
    }
}
