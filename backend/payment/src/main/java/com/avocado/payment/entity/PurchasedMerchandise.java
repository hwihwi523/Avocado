package com.avocado.payment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchasedMerchandise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    private Merchandise merchandise;

    // 구매내역
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    // 수량
    private Integer quantity;

    // 결제금액
    private Long price;

    // 사이즈
    @Column(columnDefinition = "VARCHAR(10)")
    private String size;
}
