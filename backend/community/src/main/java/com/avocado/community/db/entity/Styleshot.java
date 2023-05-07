package com.avocado.community.db.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Styleshot {
    long id;
    String content;
    String pictureUrl;
    int rating;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    UUID consumerId;
}
