package com.avocado.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mbti")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mbti {
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
