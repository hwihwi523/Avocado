package com.avocado.statistics.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatisticsDetailResp {
    List<Long> ageGenderScore;
    List<Long> mbtiScore;
    List<Long> personalColorScore;
}
