package com.avocado.payment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    @Column(columnDefinition = "VARCHAR(20)")
    private String tid;

    // 구매자
    @Column(columnDefinition = "BINARY(16)")
    private UUID consumerId;

    // 합계 가격
    private Long totalPrice;

    // 거래시간
    private LocalDateTime createdAt;

    @Builder
    public Purchase(UUID id, String tid, UUID consumerId, Long totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.tid = tid;
        this.consumerId = consumerId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt != null
                ? createdAt
                : LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
