package com.avocado.statistics.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductStatisticsDetailResp {
    List<Long> ageGenderScore;
    List<Long> mbtiScore;
    List<Long> personalColorScore;
}
