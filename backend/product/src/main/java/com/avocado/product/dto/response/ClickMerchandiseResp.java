package com.avocado.product.dto.response;

import com.avocado.product.dto.query.ClickMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClickMerchandiseResp extends DefaultMerchandiseResp {
    private Long click_id;
    private String image_url;
    private Float score;

    public ClickMerchandiseResp(ClickMerchandiseDTO clickMerchandiseDTO) {
        super.updateDefault(clickMerchandiseDTO);
        this.click_id = clickMerchandiseDTO.getClickId();
        this.image_url = clickMerchandiseDTO.getImageUrl();
        this.score = clickMerchandiseDTO.getScore() != null ? clickMerchandiseDTO.getScore() : 0;
    }
}
