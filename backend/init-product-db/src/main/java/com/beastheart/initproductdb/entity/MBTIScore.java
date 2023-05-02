package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "mbti_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MBTIScore {
    @Id
    private Long id;

    // 제품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    // MBTI
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MBTI mbti;

    // 점수
    private Long score;
}
