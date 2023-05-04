package com.avocado.product.dto.response;

import com.avocado.product.dto.query.DefaultMerchandiseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultMerchandiseResp {
    private String brand_name;
    private Long merchandise_id;
    private String merchandise_category;
    private String merchandise_name;
    private Integer price;
    private Integer discounted_price;

    private String mbti;
    private String personal_color;
    private String age_group;

    public void updateDefault(DefaultMerchandiseDTO defaultMerchandiseDTO) {
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
