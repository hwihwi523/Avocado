package com.avocado.statistics.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConsumerRecommendResp {
    List<MerchandiseResp> consumerRecommends;
    List<MerchandiseResp> personalColorRecommends;
    List<MerchandiseResp> mbtiRecommends;
}
