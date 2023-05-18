package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WishlistMerchandiseDTO extends DefaultMerchandiseDTO {
    private final Long wishlistId;
    private final Float score;

    @QueryProjection
    public WishlistMerchandiseDTO(Long wishlistId, String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                                  String merchandiseName, Integer price, Integer discountedPrice, Float score,
                                  Byte mbtiId, Byte personalColorId, Short ageGroup) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice,
                mbtiId, personalColorId, ageGroup);
        this.wishlistId = wishlistId;
        this.score = score != null ? score : 0;
    }
}
