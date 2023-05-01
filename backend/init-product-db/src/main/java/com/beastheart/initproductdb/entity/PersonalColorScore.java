package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalColorScore {
    @Id
    private Long id;

    // 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    // 퍼스널컬러
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_color_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonalColor personalColor;

    // 점수
    private Long score;
}
