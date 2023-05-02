package com.avocado.product.dto.response;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;

@Getter
public class SimpleMerchandiseResp {
    private final Long id;
    private final String brand_name;
    private final Long merchandise_id;
    private final String merchandise_category;
    private final String merchandise_name;
    private final Integer price;
    private final Integer discounted_price;
    private final Float score;

    private String mbti;
    private String personal_color;
    private String age_group;

    public SimpleMerchandiseResp(SimpleMerchandiseDTO simpleMerchandiseDTO) {
        this.id = simpleMerchandiseDTO.getId();
        this.brand_name = simpleMerchandiseDTO.getBrandName();
        this.merchandise_id = simpleMerchandiseDTO.getMerchandiseId();
        this.merchandise_category = simpleMerchandiseDTO.getMerchandiseCategory();
        this.merchandise_name = simpleMerchandiseDTO.getMerchandiseName();
        this.price = simpleMerchandiseDTO.getPrice();
        this.discounted_price = simpleMerchandiseDTO.getDiscountedPrice();
        this.score = simpleMerchandiseDTO.getScore() != null ? simpleMerchandiseDTO.getScore() : 0;
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
