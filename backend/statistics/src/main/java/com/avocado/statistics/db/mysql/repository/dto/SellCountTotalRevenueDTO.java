package com.avocado.statistics.db.mysql.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SellCountTotalRevenueDTO {
    private final Long sellCount;
    private final Long totalRevenue;

    @QueryProjection
    public SellCountTotalRevenueDTO(Long sellCount, Long totalRevenue) {
        this.sellCount = sellCount;
        this.totalRevenue = totalRevenue != null ? totalRevenue : 0L;
    }
}
