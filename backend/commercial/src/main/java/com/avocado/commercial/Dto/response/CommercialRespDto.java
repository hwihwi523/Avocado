package com.avocado.commercial.Dto.response;

import com.avocado.commercial.Dto.response.item.Carousel;
import com.avocado.commercial.Dto.response.item.Popup;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommercialRespDto {
    private Popup popup;
    private List<Carousel> carousel_list;
}
