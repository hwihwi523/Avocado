package com.avocado.statistics.db.mysql.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CategoryDistributionDTO {
    private final Short categoryId;
    private final Long count;

    @QueryProjection
    public CategoryDistributionDTO(Short categoryId, Long count) {
        this.categoryId = categoryId;
        this.count = count;
    }
}
