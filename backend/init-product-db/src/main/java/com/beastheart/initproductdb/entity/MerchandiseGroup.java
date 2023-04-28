package com.beastheart.initproductdb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MerchandiseGroup {
    @Id
    private Long id;

    // 제품 카테고리, 지연로딩 및 Cascade 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MerchandiseCategory category;

    // 제품 유형, 지연로딩 및 Cascade 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MerchandiseType type;

    // 성별 그룹, 지연로딩 및 Cascade 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_gender_group_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AgeGenderGroup ageGenderGroup;

    // 제품 용도, 지연로딩 및 Cascade 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MerchandiseUsage usage;

    // 판매자 (스토어), 지연로딩 및 Cascade 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store provider;

    // 제품 설명
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    // 정가
    private Integer price;

    // 할인가
    private Integer discountedPrice;

    // 시즌
    @Column(columnDefinition = "CHAR(3)")
    private String season;

    // 판매 연도
    private Short fashionYear;

//    @Builder
//    public MerchandiseGroup(Long id, MerchandiseCategory category, MerchandiseType type, AgeGenderGroup ageGenderGroup,
//                            MerchandiseUsage usage, Store provider, String description, Integer price, Integer discountedPrice,
//                            String season, Short fashionYear) {
//        this.id = id;
//        this.category = category;
//        this.type = type;
//        this.ageGenderGroup = ageGenderGroup;
//        this.usage = usage;
//        this.provider = provider;
//        this.description = description;
//        this.price = price;
//        this.discountedPrice = discountedPrice;
//        this.season = season;
//        this.fashionYear = fashionYear;
//    }
}
