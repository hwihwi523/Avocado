package com.avocado.statistics.api.response;

import com.avocado.statistics.db.mysql.repository.dto.MerchandiseGroupDTO;
import com.avocado.statistics.db.mysql.repository.dto.MerchandiseMainDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MerchandiseResp {
    private String brandName; // store 에서 찾아오기
    private long merchandiseId; // key. 얘로 groupId 가져와서 제품군 찾고, 거기서 판매자 아이디 찾아서 store 과 연결..
    private String merchandiseCategory; // groupId로 category 찾기
    private String merchandiseName; // merchandise 에 바로 있음
    private int price; // groupId로 group 에서 찾기
    private int discountedPrice; // groupId로 group 에서 찾기

    private String mbti; // score 테이블에서 찾아와서 제일 점수 높은 애로 데려오기
    private String personalColor; // score 테이블에서 찾아와서 제일 점수 높은 애로 데려오기
    private String ageGroup; // score 테이블에서 찾아와서 제일 점수 높은 애로 데려오기

    private String imageUrl; // merchandise에 바로 있음

    public void updateMerchandiseGroupInfo(MerchandiseGroupDTO dto) {
        this.price = dto.getPrice();
        this.discountedPrice = dto.getDiscountedPrice();
        this.merchandiseCategory = dto.getMerchandiseCategory();
        this.brandName = dto.getBrandName();
    }

    public void updateMerchandiseMainInfo(MerchandiseMainDTO dto) {
        this.merchandiseId = dto.getMerchandiseId();
        this.imageUrl = dto.getImageUrl();
        this.merchandiseName = dto.getMerchandiseName();
    }

    public void updateMBTITag(String mbtiTag) {
        this.mbti = mbtiTag;
    }

    public void updatePersonalColorTag(String personalColorTag) {
        this.personalColor = personalColorTag;
    }

    public void updateAgeGroupTag(Integer ageGroup) {
        if (ageGroup == null) {
             this.ageGroup = null;
             return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ageGroup).append("대");
        this.ageGroup =  sb.toString();
    }
}
