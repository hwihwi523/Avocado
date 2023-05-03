package com.avocado.product.dto.response;

import com.avocado.product.dto.query.WishlistMerchandiseDTO;
import lombok.Getter;

@Getter
public class WishlistMerchandiseResp extends DefaultMerchandiseResp {
    private final Long id;
    private final String image_url;
    private final Float score;

    public WishlistMerchandiseResp(WishlistMerchandiseDTO wishlistMerchandiseDTO) {
        super(wishlistMerchandiseDTO);
        this.id = wishlistMerchandiseDTO.getId();
        this.image_url = wishlistMerchandiseDTO.getImageUrl();
        this.score = wishlistMerchandiseDTO.getScore();
    }
}
