package com.avocado.search.search.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "products")
@Setting(settingPath = "static/es-setting.json")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Integer)
    private int group_id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String imgurl;

    @Field(type = FieldType.Integer)
    private int inventory;

    @Field(type = FieldType.Text)
    private String created_at;

    @Field(type = FieldType.Text)
    private String updated_at;

    @Field(type = FieldType.Text)
    private String type_kor;

    @Field(type = FieldType.Text)
    private String type_eng;

    @Field(type = FieldType.Text)
    private String age_gender_group_kor;

    @Field(type = FieldType.Text)
    private String age_gender_group_eng;

    @Field(type = FieldType.Text)
    private String usage_kor;

    @Field(type = FieldType.Text)
    private String usage_eng;

    @Field(type = FieldType.Integer)
    private int price;

    @Field(type = FieldType.Integer)
    private int discounted_price;

    @Field(type = FieldType.Text)
    private String season;

    @Field(type = FieldType.Text)
    private String store_name;

    @Field(type = FieldType.Text)
    private String fashion_year;

    @Field(type = FieldType.Text)
    private String color_kor;

    @Field(type = FieldType.Text)
    private String color_eng;

    @Field(type = FieldType.Text)
    private String sub_color1_kor;

    @Field(type = FieldType.Text)
    private String sub_color1_eng;

    @Field(type = FieldType.Text)
    private String sub_color2_kor;

    @Field(type = FieldType.Text)
    private String sub_color2_eng;

    @Field(type = FieldType.Integer)
    private int total_score;

    @Field(type = FieldType.Integer)
    private int review_count;

    @Field(type = FieldType.Text)
    private String category_kor;

    @Field(type = FieldType.Text)
    private String category_eng;
}