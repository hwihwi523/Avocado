package com.avocado.product.entity;

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

    // 구매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    // 합계 금액
    private Long totalPrice;

    // 거래시간
    private LocalDateTime createdAt;

    @Builder
    public Purchase(UUID id, Consumer consumer, Long totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.consumer = consumer;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }
}
