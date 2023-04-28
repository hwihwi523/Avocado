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
public class MBTI {
    @Id
    private Byte id;

    // MBTI 종류
    @Column(columnDefinition = "CHAR(4)")
    private String kind;

//    @Builder
//    public MBTI(Byte id, String kind) {
//        this.id = id;
//        this.kind = kind;
//    }
}
