package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class DetailMerchandiseDTO extends DefaultMerchandiseDTO {
    private final Long id;
    private final Float score;
    private final String description;

    @QueryProjection
    public DetailMerchandiseDTO(Long id, String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                                String merchandiseName, Integer price, Integer discountedPrice, Float score, String description) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice);
        this.id = id;
        this.score = score;
        this.description = description;
    }
}
