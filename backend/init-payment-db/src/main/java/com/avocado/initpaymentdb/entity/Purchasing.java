package com.avocado.initpaymentdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchasing {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    // 구매 대기자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Consumer consumer;

    // 카카오페이에서 받은 거래 ID
    @Column(columnDefinition = "VARCHAR(20)")
    private String tid;

    // 합계 금액
    private Long totalPrice;

    @Builder
    public Purchasing(UUID id, Consumer consumer, String tid, Long totalPrice) {
        this.id = id;
        this.consumer = consumer;
        this.tid = tid;
        this.totalPrice = totalPrice;
    }
}
