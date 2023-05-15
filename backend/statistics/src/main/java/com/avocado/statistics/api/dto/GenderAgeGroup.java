package com.avocado.statistics.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderAgeGroup {
    private String gender;
    private int ageGroup;
}
