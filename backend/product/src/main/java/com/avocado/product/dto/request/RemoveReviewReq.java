package com.avocado.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RemoveReviewReq {
    private final Long review_id;  // 삭제할 댓글 ID
    private final String user_id;  // 추후 삭제 예정

    @Builder
    public RemoveReviewReq(Long review_id, String user_id) {
        this.review_id = review_id;
        this.user_id = user_id;
    }
}
