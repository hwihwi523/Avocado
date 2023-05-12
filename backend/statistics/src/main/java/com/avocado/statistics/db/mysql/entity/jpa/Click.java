package com.avocado.statistics.db.mysql.entity.jpa;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Click {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여러분의 소중한 조회내역.. Cascade는 설정하지 않겠습니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    // 조회한 물품, 물건이 사라지면 조회내역도 의미없음. 따라서 Cascade 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    // 생성일자
    private LocalDateTime createdAt;

    @Builder
    public Click(Long id, Consumer consumer, Merchandise merchandise, LocalDateTime createdAt) {
        this.id = id;
        this.consumer = consumer;
        this.merchandise = merchandise;
        // 생성일자를 직접 지정하지 않는다면 현재 KST로 설정
        this.createdAt = createdAt != null
                ? createdAt
                : LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
