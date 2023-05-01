package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddReviewReq {
    private final Long id;
    private final Byte score;
    private final String content;
    private final String user_id;  // 추후 삭제 예정

    @Builder
    public AddReviewReq(Long id, Byte score, String content, String user_id) {
        this.id = id;
        this.score = score;
        this.content = content;
        this.user_id = user_id;
    }
}
