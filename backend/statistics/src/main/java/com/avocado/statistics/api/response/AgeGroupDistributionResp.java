package com.avocado.statistics.api.response;

import com.avocado.statistics.db.mysql.repository.dto.AgeGroupDistributionDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AgeGroupDistributionResp {
    private final Short age_group;
    private final Long count;

    public static AgeGroupDistributionResp from(AgeGroupDistributionDTO dto) {
        return AgeGroupDistributionResp.builder()
                .age_group(dto.getAgeGroup())
                .count(dto.getCount())
                .build();
    }

    @Builder
    public AgeGroupDistributionResp(Short age_group, Long count) {
        this.age_group = age_group;
        this.count = count;
    }
}
