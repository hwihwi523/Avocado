package com.avocado.payment.entity.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("purchasingMerchandise")
@Getter
@NoArgsConstructor
public class PurchasingMerchandise {
    private String provider_id;

    private Long merchandise_id;

    private Integer quantity;

    private Long price;

    private String size;

    @Builder
    public PurchasingMerchandise(String provider_id, Long merchandise_id, Integer quantity, Long price, String size) {
        this.provider_id = provider_id;
        this.merchandise_id = merchandise_id;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }
}
