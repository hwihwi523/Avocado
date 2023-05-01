package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    // Member 서버로부터 받아 와 저장하므로 식별자 생성 전략은 사용하지 않기
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID providerId;

    // 스토어 이름
    @Column(length = 25)
    private String name;

    // 로고 이미지 URL
    private String logoImgurl;

    // 배너 이미지 URL
    private String bannerImgurl;

    @Builder
    public Store(UUID providerId, String name, String logoImgurl, String bannerImgurl) {
        this.providerId = providerId;
        this.name = name;
        this.logoImgurl = logoImgurl;
        this.bannerImgurl = bannerImgurl;
    }
}
