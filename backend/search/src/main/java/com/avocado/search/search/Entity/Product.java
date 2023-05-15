package com.avocado.search.search.Entity;


import com.avocado.search.search.Dto.response.ProductRespDto;
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
    private int id;

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

    public ProductRespDto toDto(){
        ProductRespDto productRespDto =
                ProductRespDto.builder()
                        .id(this.id)
                        .group_id(this.group_id)
                        .name(this.name)
                        .imgurl(this.imgurl)
                        .inventory(this.inventory)
                        .created_at(this.created_at)
                        .updated_at(this.updated_at)
                        .type_kor(this.type_kor)
                        .type_eng(this.type_eng)
                        .age_gender_group_kor(this.age_gender_group_kor)
                        .age_gender_group_eng(this.age_gender_group_eng)
                        .usage_kor(this.usage_kor)
                        .usage_eng(this.usage_eng)
                        .price(this.price)
                        .discounted_price(this.discounted_price)
                        .season(this.season)
                        .store_name(this.store_name)
                        .fashion_year(this.fashion_year)
                        .color_kor(this.color_kor)
                        .color_eng(this.color_eng)
                        .sub_color1_kor(this.sub_color1_kor)
                        .sub_color1_eng(this.sub_color1_eng)
                        .sub_color2_eng(this.sub_color2_eng)
                        .sub_color2_kor(this.sub_color2_kor)
                        .total_score(this.review_count == 0 ? 0 : this.total_score/this.review_count)
                        .review_count(this.review_count)
                        .category_kor(this.category_kor)
                        .category_eng(this.category_eng)
                        .build();
        return productRespDto;
    }

}