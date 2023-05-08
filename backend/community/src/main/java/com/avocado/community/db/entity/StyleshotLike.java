package com.avocado.community.db.entity;

import lombok.Data;

@Data
public class StyleshotLike {
    private long id;
    private long styleshotId;
    private long consumerId;

}
