package com.avocado.product.dto.response;

import com.avocado.product.dto.query.CartMerchandiseDTO;
import com.avocado.product.dto.query.DefaultMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartMerchandiseResp extends DefaultMerchandiseResp {
    private Long id;
    private String image_url;
    private Float score;

    public void updateCart(CartMerchandiseDTO cartMerchandiseDTO) {
        super.updateDefault(cartMerchandiseDTO);
        this.id = cartMerchandiseDTO.getId();
        this.image_url = cartMerchandiseDTO.getImageUrl();
        this.score = cartMerchandiseDTO.getScore();
    }
}
