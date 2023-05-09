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
public class PurchasedMerchandise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 거래된 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    private Merchandise merchandise;

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Store provider;

    // 구매내역
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    // 수량
    private Integer quantity;

    // 가격
    private Long price;

    // 사이즈
    @Column(columnDefinition = "VARCHAR(10)")
    private String size;

    @Builder
    public PurchasedMerchandise(Long id, Merchandise merchandise, Store provider, Purchase purchase, Integer quantity,
                    Long price, String size, LocalDateTime createdAt) {
        this.id = id;
        this.merchandise = merchandise;
        this.provider = provider;
        this.purchase = purchase;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }
}
