package com.avocado.product.dto.response;

import com.avocado.product.dto.query.ClickMerchandiseDTO;
import lombok.Getter;

@Getter
public class ClickMerchandiseResp extends DefaultMerchandiseResp {
    private Long click_id;
    private Float score;

    public ClickMerchandiseResp(ClickMerchandiseDTO clickMerchandiseDTO) {
        super.updateDefault(clickMerchandiseDTO);
        updateClick(clickMerchandiseDTO);
    }

    public void updateClick(ClickMerchandiseDTO clickMerchandiseDTO) {
        this.click_id = clickMerchandiseDTO.getClickId();
        this.score = clickMerchandiseDTO.getScore();
    }
}
