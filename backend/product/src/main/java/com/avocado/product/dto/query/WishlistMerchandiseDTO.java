package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WishlistMerchandiseDTO extends DefaultMerchandiseDTO {
    private final Long id;
    private final Float score;

    @QueryProjection
    public WishlistMerchandiseDTO(Long id, String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                                String merchandiseName, Integer price, Integer discountedPrice, Float score) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice);
        this.id = id;
        this.score = score != null ? score : 0;
    }
}
