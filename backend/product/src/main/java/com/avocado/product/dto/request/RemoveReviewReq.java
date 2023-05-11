package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveReviewReq {
    private Long review_id;  // 삭제할 댓글 ID

    @Builder
    public RemoveReviewReq(Long review_id) {
        this.review_id = review_id;
    }
}
