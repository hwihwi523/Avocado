package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MaxTypeDTO {
    private final Long merchandiseId;
    private final String type;

    @QueryProjection
    public MaxTypeDTO(Long merchandiseId, String type) {
        this.merchandiseId = merchandiseId;
        this.type = type;
    }
}
