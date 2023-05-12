package com.avocado.statistics.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProviderStatisticsResp {
    // 조회수, 판매수, 총 판매액, 상품수 등 수로 이루어진 통계 정보
    private Long click_count;
    private Long sell_count;
    private Long total_revenue;
    private Long merchandise_count;

    // 수로 이루어진 통계 정보 업데이트
    public void updateNumericStatistics(Long click_count, Long sell_count, Long total_revenue, Long merchandise_count) {
        this.click_count = click_count;
        this.sell_count = sell_count;
        this.total_revenue = total_revenue;
        this.merchandise_count = merchandise_count;
    }
}
