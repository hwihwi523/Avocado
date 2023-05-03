package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PurchaseHistoryMerchandiseDTO extends DefaultMerchandiseDTO {
    private final UUID id;
    private final LocalDateTime purchaseDate;

    @QueryProjection
    public PurchaseHistoryMerchandiseDTO(UUID id, String brandName, Long merchandiseId, String merchandiseCategory, String imageUrl,
                                         String merchandiseName, Integer price, Integer discountedPrice, LocalDateTime purchaseDate) {
        super(brandName, merchandiseId, merchandiseCategory, imageUrl, merchandiseName, price, discountedPrice);
        this.id = id;
        this.purchaseDate = purchaseDate;
    }
}
