package com.avocado.product.dto.response;

import com.avocado.product.dto.query.DetailMerchandiseDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DetailMerchandiseResp {
    private final Long id;
    private final String brand_name;
    private final Long merchandise_id;
    private final String merchandise_category;
    private final List<String> images;
    private final String merchandise_name;
    private final Integer price;
    private final Integer discounted_price;
    private final Float score;
    private final String description;

    private String mbti;
    private String personal_color;
    private String age_group;

    private Boolean is_purchased;  // 요청한 사용자가 이 상품을 구매했는지
    private Boolean is_reviewed;  // 요청한 사용자가 이 상품에 리뷰를 남겼는지

    public DetailMerchandiseResp(DetailMerchandiseDTO detailMerchandiseDTO) {
        this.id = detailMerchandiseDTO.getId();
        this.brand_name = detailMerchandiseDTO.getBrandName();
        this.merchandise_id = detailMerchandiseDTO.getMerchandiseId();
        this.merchandise_category = detailMerchandiseDTO.getMerchandiseCategory();
        this.images = new ArrayList<>();
        this.images.add(detailMerchandiseDTO.getImageUrl());
        this.merchandise_name = detailMerchandiseDTO.getMerchandiseName();
        this.price = detailMerchandiseDTO.getPrice();
        this.discounted_price = detailMerchandiseDTO.getDiscountedPrice();
        this.score = detailMerchandiseDTO.getScore() != null ? detailMerchandiseDTO.getScore() : 0;
        this.description = detailMerchandiseDTO.getDescription();
        this.is_purchased = false;
        this.is_reviewed = false;
    }

    public void updateMBTI(String mbti) {
        this.mbti = mbti;
    }
    public void updatePersonalColor(String personal_color) {
        this.personal_color = personal_color;
    }
    public void updateAgeGroup(String ageGroup) {
        this.age_group = ageGroup + "대";
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
}
