package com.avocado.community.db.entity;

import com.avocado.community.api.request.PostStyleshotReq;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Styleshot {
    long id;
    String content;
    String pictureUrl;
    int rating;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    UUID consumerId;

    public static Styleshot of(PostStyleshotReq req, String imgUrl, UUID consumerId) {
        return Styleshot.builder()
                .content(req.getContent())
                .pictureUrl(imgUrl)
                .rating(req.getRating())
                .consumerId(consumerId).build();
    }
}
