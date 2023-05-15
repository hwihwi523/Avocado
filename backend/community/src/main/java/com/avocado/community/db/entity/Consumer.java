package com.avocado.community.db.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Consumer {
    UUID id;
    Integer ageGroup;
    String gender;
    String name;
    String pictureUrl;
    Integer mbtiId;
    Integer personalColorId;
}
