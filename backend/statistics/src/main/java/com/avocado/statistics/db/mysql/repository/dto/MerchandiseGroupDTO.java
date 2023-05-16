package com.avocado.statistics.db.mysql.repository.dto;

import lombok.Data;


@Data
public class MerchandiseGroupDTO {
    private int price;
    private int discountedPrice;
    private String merchandiseCategory;
    private String brandName;
}
