package com.avocado.product.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MaxAgeGroupDTO {
    private final Long merchandiseId;
    private final Short ageGroup;

    @QueryProjection
    public MaxAgeGroupDTO(Long merchandiseId, Short ageGroup) {
        this.merchandiseId = merchandiseId;
        this.ageGroup = ageGroup;
    }
}
