package com.avocado.commercial.Dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommercialCancelReqDto {
    private long commercial_id;
}
