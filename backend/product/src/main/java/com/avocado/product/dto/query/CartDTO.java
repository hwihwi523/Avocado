package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CartDTO {
    private Long cart_id;
    private String brand_name;
    private Long merchandise_id;
    private String merchandise_name;
    private Integer price;
    private Integer discounted_price;
    private Float score;

    @QueryProjection
    public CartDTO(Long cart_id, String brand_name, Long merchandise_id, String merchandise_name,
                   Integer price, Integer discounted_price, Float score) {
        this.cart_id = cart_id;
        this.brand_name = brand_name;
        this.merchandise_id = merchandise_id;
        this.merchandise_name = merchandise_name;
        this.price = price;
        this.discounted_price = discounted_price;
        this.score = score != null ? score : 0;
    }
}
