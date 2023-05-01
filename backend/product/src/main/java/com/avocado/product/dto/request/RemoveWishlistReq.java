package com.avocado.product.dto.request;

import lombok.Getter;

@Getter
public class RemoveWishlistReq {
    String user_id;
    Long wishlist_id;
}
