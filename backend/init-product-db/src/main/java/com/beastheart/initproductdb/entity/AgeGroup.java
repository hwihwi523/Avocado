package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    // 성별 (한국어)
    @Column(length = 30)
    private String nameKor;

    // 성별 (영어)
    @Column(length = 30)
    private String nameEng;

    @Builder
    public AgeGroup(Short id, String nameKor, String nameEng) {
        this.id = id;
        this.nameKor = nameKor;
        this.nameEng = nameEng;
    }
}
