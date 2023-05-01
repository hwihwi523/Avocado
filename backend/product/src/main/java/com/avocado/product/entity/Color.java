package com.avocado.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Color {
    @Id
    private Long id;

    // 색상명 (한국어)
    @Column(length = 30)
    private String nameKor;

    // 색상명 (영어)
    @Column(length = 30)
    private String nameEng;

//    @Builder
//    public Color(Long id, String nameKor, String nameEng) {
//        this.id = id;
//        this.nameKor = nameKor;
//        this.nameEng = nameEng;
//    }
}
