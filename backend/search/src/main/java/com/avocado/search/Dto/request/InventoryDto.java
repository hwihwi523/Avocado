package com.avocado.search.Dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    private long id;
    private int inventory;
}
