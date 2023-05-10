package com.avocado.payment.entity.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash(value = "purchasing", timeToLive = 1800)
@Getter
@NoArgsConstructor
public class Purchasing {
    @Id
    private String id;

    private String consumer_id;

    private Long total_price;

    private String tid;

    private List<PurchasingMerchandise> merchandises;

    @Builder
    public Purchasing(String id, String consumer_id, Long total_price, String tid, List<PurchasingMerchandise> merchandises) {
        this.id = id;
        this.consumer_id = consumer_id;
        this.total_price = total_price;
        this.tid = tid;
        this.merchandises = merchandises;
    }
}
