package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddReviewReq {
    private Long id;
    private Byte score;
    private String content;

    @Builder
    public AddReviewReq(Long id, Byte score, String content) {
        this.id = id;
        this.score = score;
        this.content = content;
    }
}
