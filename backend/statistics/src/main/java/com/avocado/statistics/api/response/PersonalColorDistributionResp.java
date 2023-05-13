package com.avocado.statistics.api.response;

import com.avocado.statistics.common.enums.PersonalColor;
import com.avocado.statistics.db.mysql.repository.dto.ChartDistributionDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalColorDistributionResp {
    private final String kind;
    private final Long count;

    public static PersonalColorDistributionResp from(ChartDistributionDTO dto) {
        return PersonalColorDistributionResp.builder()
                .kind(PersonalColor.getPersonalColor(dto.getId()))
                .count(dto.getCount())
                .build();
    }

    @Builder
    public PersonalColorDistributionResp(String kind, Long count) {
        this.kind = kind;
        this.count = count;
    }
}
