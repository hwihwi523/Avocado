package com.avocado.statistics.db.mysql.repository.dto;

import lombok.Data;

@Data
public class MerchandiseMainDTO {
    private long merchandiseId;
    private String imageUrl;
    private String merchandiseName;
    private long groupId;
}
