package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartReq {
    private Long merchandise_id;

    @Builder
    public AddCartReq(Long merchandise_id) {
        this.merchandise_id = merchandise_id;
    }
}
