package com.avocado.statistics.kafka.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Result {
    private String userId;
    private Long merchandiseId;
    private Type type;
}
