package com.avocado.statistics.kafka.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
public class AdStatus {
    private String date;
    private List<Status> statusList;
}
