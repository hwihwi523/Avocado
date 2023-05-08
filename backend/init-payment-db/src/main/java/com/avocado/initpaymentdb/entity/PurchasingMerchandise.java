package com.avocado.initpaymentdb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchasingMerchandise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매대기내역
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchasing_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Purchasing purchasing;

    // 상품
    private Long merchandiseId;
}
