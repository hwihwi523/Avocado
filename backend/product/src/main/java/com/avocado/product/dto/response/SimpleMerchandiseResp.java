package com.avocado.product.dto.response;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import lombok.Getter;

@Getter
public class SimpleMerchandiseResp extends DefaultMerchandiseResp {
    private final Long id;
    private final String image_url;
    private final Float score;

    private String mbti;
    private String personal_color;
    private String age_group;

    public SimpleMerchandiseResp(SimpleMerchandiseDTO simpleMerchandiseDTO) {
        super(simpleMerchandiseDTO);
        this.id = simpleMerchandiseDTO.getId();
        this.image_url = simpleMerchandiseDTO.getImageUrl();
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
