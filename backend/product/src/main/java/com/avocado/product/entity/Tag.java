package com.avocado.product.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchandise_id")
    private Merchandise merchandise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_color_id")
    private PersonalColor personalColor;

    private Short ageGroup;

    @Builder
    public Tag(Long id, Merchandise merchandise, Mbti mbti, PersonalColor personalColor, Short ageGroup) {
        this.id = id;
        this.merchandise = merchandise;
        this.mbti = mbti;
        this.personalColor = personalColor;
        this.ageGroup = ageGroup;
    }

    public void updateMbti(Mbti mbti) {
        this.mbti = mbti;
    }

    public void updatePersonalColor(PersonalColor personalColor) {
        this.personalColor = personalColor;
    }

    public void updateAgeGroup(Short ageGroup) {
        this.ageGroup = ageGroup;
    }
}
