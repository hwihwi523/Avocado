package com.avocado.statistics.db.mysql.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GenderDistributionDTO {
    private final String gender;
    private final Long count;

    @QueryProjection
    public GenderDistributionDTO(String gender, Long count) {
        this.gender = gender;
        this.count = count;
    }
}
