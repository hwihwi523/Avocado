package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SimpleMerchandiseDTO {
    private Long id;
    private String brand_name;
    private Long merchandise_id;
    private String merchandise_category;
    private String merchandise_name;
    private Integer price;
    private Integer discounted_price;
    private Float score;

    @QueryProjection
    public SimpleMerchandiseDTO(Long id, String brand_name, Long merchandise_id, String merchandise_category, String merchandise_name,
                                Integer price, Integer discounted_price, Float score) {
        this.id = id;
        this.brand_name = brand_name;
        this.merchandise_id = merchandise_id;
        this.merchandise_category = merchandise_category;
        this.merchandise_name = merchandise_name;
        this.price = price;
        this.discounted_price = discounted_price;
        this.score = score != null ? score : 0;
    }
}
