package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveCartReq {
    private Long cart_id;

    @Builder
    public RemoveCartReq(Long cart_id) {
        this.cart_id = cart_id;
    }
}
