package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MaxMbtiDTO {
    private final Long merchandiseId;
    private final Byte mbtiId;

    @QueryProjection
    public MaxMbtiDTO(Long merchandiseId, Byte mbtiId) {
        this.merchandiseId = merchandiseId;
        this.mbtiId = mbtiId;
    }
}
