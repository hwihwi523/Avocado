package com.avocado.statistics.api.response;

import com.avocado.statistics.db.mysql.repository.dto.GenderDistributionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenderDistributionResp {
    private String gender;
    private Long count;

    public static GenderDistributionResp from(GenderDistributionDTO dto) {
        return GenderDistributionResp.builder()
                .gender(dto.getGender())
                .count(dto.getCount())
                .build();
    }

    @Builder
    public GenderDistributionResp(String gender, Long count) {
        this.gender = gender;
        this.count = count;
    }
}
