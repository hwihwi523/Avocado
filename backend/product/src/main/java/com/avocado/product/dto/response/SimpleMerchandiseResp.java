package com.avocado.product.dto.response;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleMerchandiseResp extends DefaultMerchandiseResp {
    private String image_url;
    private Float score;

    public SimpleMerchandiseResp(SimpleMerchandiseDTO simpleMerchandiseDTO) {
        super.updateDefault(simpleMerchandiseDTO);
        this.image_url = simpleMerchandiseDTO.getImageUrl();
        this.score = simpleMerchandiseDTO.getScore() != null ? simpleMerchandiseDTO.getScore() : 0;
    }
}
