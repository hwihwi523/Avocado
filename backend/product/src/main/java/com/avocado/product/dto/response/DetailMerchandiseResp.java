package com.avocado.product.dto.response;

import com.avocado.product.dto.query.DetailMerchandiseDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DetailMerchandiseResp extends DefaultMerchandiseResp {
    private String provider_id;
    private List<String> images;
    private Integer inventory;
    private Float score;
    private String description;

    private Boolean is_purchased;  // 요청한 사용자가 이 상품을 구매했는지
    private Boolean is_reviewed;  // 요청한 사용자가 이 상품에 리뷰를 남겼는지
    private Boolean is_wishlist;  // 요청한 사용자가 이 상품을 찜했는지

    private List<SimpleMerchandiseResp> related;

    public DetailMerchandiseResp(DetailMerchandiseDTO detailMerchandiseDTO) {
        super();
        updateDetail(detailMerchandiseDTO);
    }

    public void updateDetail(DetailMerchandiseDTO detailMerchandiseDTO) {
        super.updateDefault(detailMerchandiseDTO);
        this.provider_id = detailMerchandiseDTO.getProviderId().toString().replace("-", "");
        this.images = new ArrayList<>();
        this.images.add(detailMerchandiseDTO.getImageUrl());
        this.inventory = detailMerchandiseDTO.getInventory();
        this.score = detailMerchandiseDTO.getScore() != null ? detailMerchandiseDTO.getScore() : 0;
        this.description = detailMerchandiseDTO.getDescription();
        this.is_purchased = false;
        this.is_reviewed = false;
        this.is_wishlist = false;
    }

    public void updateImages(List<String> images) {
        this.images.addAll(images);
    }
    public void updateIsPurchased(Boolean is_purchased) {
        this.is_purchased = is_purchased;
    }
    public void updateIsReviewed(Boolean is_reviewed) {
        this.is_reviewed = is_reviewed;
    }
    public void updateIsWishlist(Boolean is_wishlist) {
        this.is_wishlist = is_wishlist;
    }
    public void updateRelated(List<SimpleMerchandiseResp> related) {
        this.related = related;
    }
}
