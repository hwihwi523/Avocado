package com.avocado.product.dto.response;

import com.avocado.product.dto.query.WishlistMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishlistMerchandiseResp extends DefaultMerchandiseResp {
    private Long wishlist_id;
    private String image_url;
    private Float score;

    public void updateWishlist(WishlistMerchandiseDTO wishlistMerchandiseDTO) {
        super.updateDefault(wishlistMerchandiseDTO);
        this.wishlist_id = wishlistMerchandiseDTO.getWishlistId();
        this.image_url = wishlistMerchandiseDTO.getImageUrl();
        this.score = wishlistMerchandiseDTO.getScore();
    }
}
