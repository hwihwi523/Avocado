package com.avocado.statistics.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result {
    private String userId;
    private Long merchandiseId;
    private Type type;
}
