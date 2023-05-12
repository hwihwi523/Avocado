package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddWishlistReq {
    private Long merchandise_id;

    @Builder
    public AddWishlistReq(Long merchandise_id) {
        this.merchandise_id = merchandise_id;
    }
}
