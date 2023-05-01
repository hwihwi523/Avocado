package com.avocado.product.dto.response;

import com.avocado.product.dto.query.ReviewDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ReviewResp {
    private final Long id;
    private final String reviewer;
    private final String picture_url;
    private final String mbti;
    private final String personal_color;
    private final Byte score;
    private final String content;
    private final String created_at;

    public ReviewResp(ReviewDTO reviewDTO) {
        this.id = reviewDTO.getId();
        this.reviewer = reviewDTO.getReviewer();
        this.picture_url = reviewDTO.getPicture_url();
        this.mbti = reviewDTO.getMbti();
        this.personal_color = reviewDTO.getPersonal_color();
        this.score = reviewDTO.getScore();
        this.content = reviewDTO.getContent();
        this.created_at = reviewDTO.getCreated_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
