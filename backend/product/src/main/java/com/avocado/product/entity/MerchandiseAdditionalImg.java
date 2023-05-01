package com.avocado.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(MerchandiseAdditionalImgId.class)
public class MerchandiseAdditionalImg {
    @Id
    @Column(length = 10)
    private String kind;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchandise merchandise;

    // 이미지 URL
    private String imgurl;

//    @Builder
//    public MerchandiseAdditionalImg(String kind, Merchandise merchandise, String imgurl) {
//        this.kind = kind;
//        this.merchandise = merchandise;
//        this.imgurl = imgurl;
//    }
}
