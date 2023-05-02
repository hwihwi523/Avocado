package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ScoreDTO {
    private final Long merchandiseId;
    private final String type;
    private final Long count;

    @QueryProjection
    public ScoreDTO(Long merchandiseId, String type, Long count) {
        this.merchandiseId = merchandiseId;
        this.type = type;
        this.count = count;
    }
}
