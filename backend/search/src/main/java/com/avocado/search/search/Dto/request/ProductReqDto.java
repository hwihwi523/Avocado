package com.avocado.search.search.Dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {
    private int id;
    private int inventory;
    private int review_count;
    private int total_score;
}
