package com.avocado.payment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Store provider;

    // 합계 가격
    private Long total_price;

    // 거래시간
    private LocalDateTime createdAt;

    @Builder
    public Purchase(UUID id, Consumer consumer, Store provider, Long total_price) {
        this.id = id;
        this.consumer = consumer;
        this.provider = provider;
        this.total_price = total_price;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
