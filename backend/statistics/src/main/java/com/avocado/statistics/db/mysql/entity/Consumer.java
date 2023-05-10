package com.avocado.statistics.db.mysql.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Consumer {

    UUID id;
    String name;
    String email;
    String pictureUrl;
    String gender;
    Integer ageGroup;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Integer height;
    Integer weight;
    Integer mbtiId;
    Integer personalColorId;
    int auth;

}
