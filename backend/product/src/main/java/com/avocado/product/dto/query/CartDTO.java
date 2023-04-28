package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CartDTO {
    private Long id;
    private String brand_name;
    private String merchandise_name;
    private Integer price;
    private Integer discounted_price;
//    private Float score;

    @QueryProjection
    public CartDTO(Long id, String brand_name, String merchandise_name, Integer price, Integer discounted_price) {
        this.id = id;
        this.brand_name = brand_name;
        this.merchandise_name = merchandise_name;
        this.price = price;
        this.discounted_price = discounted_price;
    }
}
