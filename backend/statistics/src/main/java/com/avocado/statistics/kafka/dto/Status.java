package com.avocado.statistics.kafka.dto;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Status {
    private Long merchandiseId;
    private Integer exposureCnt;
    private Integer clickCnt;
    private Integer quantity;
}
