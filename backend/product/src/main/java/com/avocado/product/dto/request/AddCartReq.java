package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartReq {
    private Long merchandise_id;
    private Integer quantity;
    private String size;

    @Builder
    public AddCartReq(Long merchandise_id, Integer quantity, String size) {
        this.merchandise_id = merchandise_id;
        this.quantity = quantity;
        this.size = size;
    }
}
