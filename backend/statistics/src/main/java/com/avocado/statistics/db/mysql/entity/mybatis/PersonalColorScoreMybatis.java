package com.avocado.statistics.db.mysql.entity.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalColorScoreMybatis {
    private long score;
    private int personalColorId;
    private long merchandiseId;
}
