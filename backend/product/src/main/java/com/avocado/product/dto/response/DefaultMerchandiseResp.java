package com.avocado.product.dto.response;

import com.avocado.product.dto.query.DefaultMerchandiseDTO;
import lombok.Getter;

@Getter
public class DefaultMerchandiseResp {
    private final String brand_name;
    private final Long merchandise_id;
    private final String merchandise_category;
    private final String merchandise_name;
    private final Integer price;
    private final Integer discounted_price;

    private String mbti;
    private String personal_color;
    private String age_group;

    public DefaultMerchandiseResp(DefaultMerchandiseDTO defaultMerchandiseDTO) {
        this.brand_name = defaultMerchandiseDTO.getBrandName();
        this.merchandise_id = defaultMerchandiseDTO.getMerchandiseId();
        this.merchandise_category = defaultMerchandiseDTO.getMerchandiseCategory();
        this.merchandise_name = defaultMerchandiseDTO.getMerchandiseName();
        this.price = defaultMerchandiseDTO.getPrice();
        this.discounted_price = defaultMerchandiseDTO.getDiscountedPrice();
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
}
