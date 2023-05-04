package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ClickMerchandiseDTO extends DefaultMerchandiseDTO {
    private final Long clickId;
    private final Float score;

    @QueryProjection
    public ClickMerchandiseDTO(Long clickId, String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                               String merchandiseName, Integer price, Integer discountedPrice, Float score) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice);
        this.clickId = clickId;
        this.score = score;
    }
}
