package com.avocado.product.dto.response;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleMerchandiseResp extends DefaultMerchandiseResp {
    private String image_url;
    private Float score;

    private Boolean is_wishlist;

    public void updateSimple(SimpleMerchandiseDTO simpleMerchandiseDTO) {
        super.updateDefault(simpleMerchandiseDTO);
        this.image_url = simpleMerchandiseDTO.getImageUrl();
        this.score = simpleMerchandiseDTO.getScore() != null ? simpleMerchandiseDTO.getScore() : 0;
        this.is_wishlist = false;
    }

    public void updateIsWishlist(Boolean is_wishlist) {
        this.is_wishlist = is_wishlist;
    }
}
