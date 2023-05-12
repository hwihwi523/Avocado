package com.avocado.statistics.db.mysql.entity.mybatis;

import lombok.Data;

import java.util.UUID;

@Data
public class Consumer {

    UUID id;
    String name;
    String pictureUrl;
    String gender;
    Integer age;
    Integer personalColorId;
    Integer mbtiId;
}
