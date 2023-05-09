package com.avocado.commercial.Dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class Analysis {
    private String date;
    private long exposure_cnt;
    private long click_cnt;
    private long purchase_amount;
    private long quantity;
}
