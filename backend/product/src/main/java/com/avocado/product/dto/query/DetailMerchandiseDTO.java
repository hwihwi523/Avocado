package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DetailMerchandiseDTO extends DefaultMerchandiseDTO {
    private final UUID providerId;
    private final Integer inventory;
    private final Float score;
    private final String description;

    @QueryProjection
    public DetailMerchandiseDTO(String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                                String merchandiseName, Integer price, Integer discountedPrice, UUID providerId,
                                Integer inventory, Float score, String description, Byte mbtiId, Byte personalColorId,
                                Short ageGroup) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice,
                mbtiId, personalColorId, ageGroup);
        this.providerId = providerId;
        this.inventory = inventory;
        this.score = score;
        this.description = description;
    }
}
