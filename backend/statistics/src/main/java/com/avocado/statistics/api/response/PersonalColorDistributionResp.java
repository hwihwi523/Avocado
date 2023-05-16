package com.avocado.statistics.api.response;

import com.avocado.statistics.common.enums.PersonalColor;
import com.avocado.statistics.db.mysql.repository.dto.ChartDistributionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PersonalColorDistributionResp {
    private String kind;
    private Long count;

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
