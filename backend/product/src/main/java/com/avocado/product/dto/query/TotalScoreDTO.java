package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TotalScoreDTO {
    private final Long merchandiseId;
    private final String type;
    private final Long score;

    @QueryProjection
    public TotalScoreDTO(Long merchandiseId, String type, Long score) {
        this.merchandiseId = merchandiseId;
        this.type = type;
        this.score = score;
    }
}
