package com.avocado.search.Dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private int id;
    private int review_count;
    private int total_score;
}
