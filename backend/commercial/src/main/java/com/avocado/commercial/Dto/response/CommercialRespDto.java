package com.avocado.commercial.Dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CommercialRespDto {
    private long merchandise_id;
    private String imgurl;
}
