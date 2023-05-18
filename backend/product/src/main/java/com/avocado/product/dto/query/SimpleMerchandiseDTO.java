package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SimpleMerchandiseDTO extends DefaultMerchandiseDTO {
    private final Float score;

    @QueryProjection
    public SimpleMerchandiseDTO(String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                                String merchandiseName, Integer price, Integer discountedPrice, Float score,
                                Byte mbtiId, Byte personalColorId, Short ageGroup) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice,
                mbtiId, personalColorId, ageGroup);
        this.score = score != null ? score : 0;
    }
}
