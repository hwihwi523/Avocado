package com.avocado.product.dto.request;

import lombok.Getter;

@Getter
public class AddWishlistReq {
    private Long merchandise_id;
    private String user_id;
}
