package com.avocado.statistics.db.mysql.entity.jpa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MerchandiseType {
    @Id
    private Long id;

    // 제품의 종류명 (한국어)
    @Column(length = 100)
    private String nameKor;

    // 제품의 종류명 (영어)
    @Column(length = 100)
    private String nameEng;

//    @Builder
//    public MerchandiseType(Long id, String nameKor, String nameEng) {
//        this.id = id;
//        this.nameKor = nameKor;
//        this.nameEng = nameEng;
//    }
}
