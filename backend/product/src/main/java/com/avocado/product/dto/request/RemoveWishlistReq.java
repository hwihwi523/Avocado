package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveWishlistReq {
    private Long wishlist_id;

    @Builder
    public RemoveWishlistReq(Long wishlist_id) {
        this.wishlist_id = wishlist_id;
    }
}
