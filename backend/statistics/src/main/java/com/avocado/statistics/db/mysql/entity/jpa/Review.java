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
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매자 ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Consumer consumer;

    // 상품 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    // 댓글 내용
    private String content;

    // 별점
    private Byte score;

    // 생성일자 & 수정일자
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Review(Long id, Consumer consumer, Merchandise merchandise, String content, Byte score,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.consumer = consumer;
        this.merchandise = merchandise;
        this.content = content;
        this.score = score;
        this.createdAt = createdAt != null
                ? createdAt
                : LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.updatedAt = updatedAt != null
                ? updatedAt
                : this.createdAt;
    }
}
