package com.avocado.product.dto.response;

import com.avocado.product.dto.query.ClickMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClickMerchandiseResp extends DefaultMerchandiseResp {
    private Long click_id;
    private Float score;

    public void updateClick(ClickMerchandiseDTO clickMerchandiseDTO) {
        super.updateDefault(clickMerchandiseDTO);
        this.click_id = clickMerchandiseDTO.getClickId();
        this.score = clickMerchandiseDTO.getScore() != null ? clickMerchandiseDTO.getScore() : 0;
    }
}
