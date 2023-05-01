package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDTO {
    private final Long id;
    private final String reviewer;
    private final String picture_url;
    private final String mbti;
    private final String personal_color;
    private final Byte score;
    private final String content;
    private final LocalDateTime created_at;

    @QueryProjection
    public ReviewDTO(Long id, String reviewer, String picture_url, String mbti, String personal_color,
                     Byte score, String content, LocalDateTime created_at) {
        this.id = id;
        this.reviewer = reviewer;
        this.picture_url = picture_url;
        this.mbti = mbti;
        this.personal_color = personal_color;
        this.score = score;
        this.content = content;
        this.created_at = created_at;
    }
}
