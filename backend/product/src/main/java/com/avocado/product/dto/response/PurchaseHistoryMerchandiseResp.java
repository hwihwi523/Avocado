package com.avocado.product.dto.response;

import com.avocado.product.dto.query.PurchaseHistoryMerchandiseDTO;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PurchaseHistoryMerchandiseResp extends DefaultMerchandiseResp {
    private String purchase_id;
    private LocalDateTime purchase_date;
    private String image_url;
    private String size;

    public void updatePurchaseHistory(PurchaseHistoryMerchandiseDTO purchaseHistoryMerchandiseDTO) {
        super.updateDefault(purchaseHistoryMerchandiseDTO);
        this.purchase_id = purchaseHistoryMerchandiseDTO.getPurchaseId().toString().replace("-", "");
        this.purchase_date = purchaseHistoryMerchandiseDTO.getPurchaseDate();
        this.image_url = purchaseHistoryMerchandiseDTO.getImageUrl();
        this.size = purchaseHistoryMerchandiseDTO.getSize();
    }
}
