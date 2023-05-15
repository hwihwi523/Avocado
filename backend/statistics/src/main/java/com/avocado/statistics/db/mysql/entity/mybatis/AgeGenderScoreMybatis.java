package com.avocado.statistics.db.mysql.entity.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgeGenderScoreMybatis {
    private int age;
    private String gender;
    private long score;
    private long merchandiseId;
}
