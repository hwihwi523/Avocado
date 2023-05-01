package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 찜꽁한 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Consumer consumer;

    // 찜꽁한 제품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    @Builder
    public Interest(Long id, Consumer consumer, Merchandise merchandise) {
        this.id = id;
        this.consumer = consumer;
        this.merchandise = merchandise;
    }
}
