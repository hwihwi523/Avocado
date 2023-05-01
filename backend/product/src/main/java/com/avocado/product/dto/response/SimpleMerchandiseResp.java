package com.avocado.product.dto.response;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;

@Getter
public class SimpleMerchandiseResp {
    private Long id;
    private String brand_name;
    private Long merchandise_id;
    private String merchandise_category;
    private String merchandise_name;
    private Integer price;
    private Integer discounted_price;
    private Float score;

    private String mbti;
    private String personal_color;
    private String age_group;

    public SimpleMerchandiseResp(SimpleMerchandiseDTO simpleMerchandiseDTO) {
        this.id = simpleMerchandiseDTO.getId();
        this.brand_name = simpleMerchandiseDTO.getBrand_name();
        this.merchandise_id = simpleMerchandiseDTO.getMerchandise_id();
        this.merchandise_category = simpleMerchandiseDTO.getMerchandise_category();
        this.merchandise_name = simpleMerchandiseDTO.getMerchandise_name();
        this.price = simpleMerchandiseDTO.getPrice();
        this.discounted_price = simpleMerchandiseDTO.getDiscounted_price();
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
