package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RemoveWishlistReq {
    private final String user_id;
    private final Long wishlist_id;

    @Builder
    public RemoveWishlistReq(String user_id, Long wishlist_id) {
        this.user_id = user_id;
        this.wishlist_id = wishlist_id;
    }
}
