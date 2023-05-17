package com.avocado.commercial.Dto.response.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Carousel {
    private String imgurl;
    private long merchandise_id;
    private String name;
}
