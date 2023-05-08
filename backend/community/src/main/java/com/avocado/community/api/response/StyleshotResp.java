package com.avocado.community.api.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StyleshotResp {
    long id;
    String content;
    String pictureUrl;
    int rating;
    LocalDateTime createdAt;
    List<Long> wears;
}
