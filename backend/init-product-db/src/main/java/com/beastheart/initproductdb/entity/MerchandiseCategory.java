package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MerchandiseCategory {
    @Id
    private Short id;

    // 카테고리명 (한국어)
    @Column(length = 100)
    private String nameKor;

    // 카테고리명 (영어)
    @Column(length = 100)
    private String nameEng;

//    @Builder
//    public MerchandiseCategory(Short id, String nameKor, String nameEng) {
//        this.id = id;
//        this.nameKor = nameKor;
//        this.nameEng = nameEng;
//    }
}
