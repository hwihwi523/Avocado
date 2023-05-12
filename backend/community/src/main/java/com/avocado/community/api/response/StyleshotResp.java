package com.avocado.community.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StyleshotResp {
    long id;
    String content;
    String pictureUrl;
    int rating;
    LocalDateTime createdAt;
    List<MerchandiseResp> wears;
}
