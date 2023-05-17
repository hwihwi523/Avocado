package com.avocado.search.Dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@ToString
public class ProductRespDto {
    private int id;

    private int group_id;

    private String name;

    private String imgurl;

    private int inventory;

    private String created_at;

    private String updated_at;

    private String type_kor;

    private String type_eng;

    private String age_gender_group_kor;

    private String age_gender_group_eng;

    private String usage_kor;

    private String usage_eng;

    private int price;

    private int discounted_price;

    private String season;

    private String store_name;

    private String fashion_year;

    private String color_kor;

    private String color_eng;

    private String sub_color1_kor;

    private String sub_color1_eng;

    private String sub_color2_kor;

    private String sub_color2_eng;

    private int total_score;

    private int review_count;

    private String category_kor;

    private String category_eng;
}
