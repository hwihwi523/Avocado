package com.avocado.community.db.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Consumer {
    UUID id;
    String name;
    String pictureUrl;
    LocalDateTime createdAt;
}
