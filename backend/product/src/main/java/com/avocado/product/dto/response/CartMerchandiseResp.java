package com.avocado.product.dto.response;

import com.avocado.product.dto.query.CartMerchandiseDTO;
import com.avocado.product.dto.query.DefaultMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartMerchandiseResp extends DefaultMerchandiseResp {
    private Long cart_id;
    private String image_url;
    private Float score;
    private String size;

    public void updateCart(CartMerchandiseDTO cartMerchandiseDTO) {
        super.updateDefault(cartMerchandiseDTO);
        this.cart_id = cartMerchandiseDTO.getCartId();
        this.image_url = cartMerchandiseDTO.getImageUrl();
        this.score = cartMerchandiseDTO.getScore();
        this.size = cartMerchandiseDTO.getSize();
    }
}
