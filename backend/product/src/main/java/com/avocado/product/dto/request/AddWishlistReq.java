package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddWishlistReq {
    private final Long merchandise_id;
    private final String user_id;

    @Builder
    public AddWishlistReq(String user_id, Long merchandise_id) {
        this.user_id = user_id;
        this.merchandise_id = merchandise_id;
    }
}
