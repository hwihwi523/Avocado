package com.avocado.statistics.db.mysql.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ChartDistributionDTO {
    private final Byte id;
    private final Long count;

    @QueryProjection
    public ChartDistributionDTO(Byte id, Long count) {
        this.id = id;
        this.count = count;
    }
}
