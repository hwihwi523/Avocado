package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalColor {
    @Id
    private Byte id;

    // 퍼스널컬러 종류
    @Column(length = 100)
    private String kind;

//    @Builder
//    public PersonalColor(Byte id, String kind) {
//        this.id = id;
//        this.kind = kind;
//    }
}
