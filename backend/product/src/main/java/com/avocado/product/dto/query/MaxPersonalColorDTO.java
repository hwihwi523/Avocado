package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MaxPersonalColorDTO {
    private final Long merchandiseId;
    private final Byte personalColorId;

    @QueryProjection
    public MaxPersonalColorDTO(Long merchandiseId, Byte personalColorId) {
        this.merchandiseId = merchandiseId;
        this.personalColorId = personalColorId;
    }
}
