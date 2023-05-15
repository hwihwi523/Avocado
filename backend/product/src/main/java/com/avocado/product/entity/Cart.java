package com.avocado.product.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 담은 고객
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Consumer consumer;

    // 담긴 물건
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    // 수량
    private Integer quantity;

    // 사이즈
    @Column(columnDefinition = "VARCHAR(10)")
    private String size;

    @Builder
    public Cart(Long id, Consumer consumer, Merchandise merchandise, Integer quantity, String size) {
        this.id = id;
        this.consumer = consumer;
        this.merchandise = merchandise;
        this.quantity = quantity;
        this.size = size;
    }

    // 수량 증가
    public void addQuantities(int quantity) {
        this.quantity += quantity;
    }
}
