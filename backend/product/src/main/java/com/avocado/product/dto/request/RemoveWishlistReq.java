package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveWishlistReq {
    private Long merchandise_id;

    @Builder
    public RemoveWishlistReq(Long merchandise_id) {
        this.merchandise_id = merchandise_id;
    }
}
