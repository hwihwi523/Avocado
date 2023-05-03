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
}
