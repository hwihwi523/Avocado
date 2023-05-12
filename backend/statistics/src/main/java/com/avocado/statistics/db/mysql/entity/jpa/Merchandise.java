package com.avocado.statistics.db.mysql.entity.jpa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchandise {
    @Id
    private Long id;

    // 제품군
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MerchandiseGroup group;

    // 기본 색상
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_color_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Color baseColor;

    // 보조 색상 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color1_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Color color1;

    // 보조 색상 2
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color2_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Color color2;

    // 제품명
    private String name;

    // 상품 기본 이미지 URL
    private String imgurl;

    // 재고
    private Integer inventory;

    // 별점 합계 & 리뷰 개수
    private Long totalScore;
    private Long reviewCount;

    // 생성일자 & 수정일자
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    @Builder
//    public Merchandise(Long id, MerchandiseGroup group, Color baseColor, Color color1, Color color2,
//                       String name, String imgurl, Integer inventory, LocalDateTime createdAt, LocalDateTime updatedAt) {
//        this.id = id;
//        this.group = group;
//        this.baseColor = baseColor;
//        this.color1 = color1;
//        this.color2 = color2;
//        this.name = name;
//        this.imgurl = imgurl;
//        this.inventory = inventory;
//        this.createdAt = createdAt != null
//                ? createdAt
//                : LocalDateTime.now(ZoneId.of("Asia/Seoul"));
//        this.updatedAt = updatedAt != null
//                ? updatedAt
//                : this.createdAt;
//    }
}
