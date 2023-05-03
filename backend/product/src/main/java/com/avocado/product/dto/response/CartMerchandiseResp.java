package com.avocado.product.dto.response;

import com.avocado.product.dto.query.CartMerchandiseDTO;
import com.avocado.product.dto.query.DefaultMerchandiseDTO;
import lombok.Getter;

@Getter
public class CartMerchandiseResp extends DefaultMerchandiseResp {
    private final Long id;
    private final String image_url;
    private final Float score;

    public CartMerchandiseResp(CartMerchandiseDTO cartMerchandiseDTO) {
        super(cartMerchandiseDTO);
        this.id = cartMerchandiseDTO.getId();
        this.image_url = cartMerchandiseDTO.getImageUrl();
        this.score = cartMerchandiseDTO.getScore();
    }
}
