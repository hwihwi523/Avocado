package com.avocado.commercial.Dto.response;

import lombok.*;


@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    private String date;
    private long exposure_cnt;
    private long click_cnt;
    private long purchase_amount;
    private long quantity;
}
