package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    // 거래된 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    private Merchandise merchandise;

    // 구매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Store provider;

    // 수량
    private Integer quantity;
    // 가격
    private Long price;
    // 사이즈
    @Column(columnDefinition = "VARCHAR(10)")
    private String size;
    // 거래시간
    private LocalDateTime createdAt;

    @Builder
    public Purchase(UUID id, Merchandise merchandise, Consumer consumer, Store provider, Integer quantity,
                    Long price, String size, LocalDateTime createdAt) {
        this.id = id;
        this.merchandise = merchandise;
        this.consumer = consumer;
        this.provider = provider;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.createdAt = createdAt;
    }
}
