package com.avocado.community.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StyleshotPagingResp {
    private boolean isLastPage;
    private Long lastId;
    private List<StyleshotResp> styleshotList;

}
