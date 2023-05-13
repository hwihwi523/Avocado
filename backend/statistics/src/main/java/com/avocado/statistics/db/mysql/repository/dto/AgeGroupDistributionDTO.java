package com.avocado.statistics.db.mysql.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class AgeGroupDistributionDTO {
    private final Short ageGroup;
    private final Long count;

    @QueryProjection
    public AgeGroupDistributionDTO(Short ageGroup, Long count) {
        this.ageGroup = ageGroup;
        this.count = count;
    }
}
