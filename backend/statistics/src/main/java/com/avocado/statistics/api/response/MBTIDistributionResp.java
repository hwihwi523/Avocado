package com.avocado.statistics.api.response;

import com.avocado.statistics.common.enums.MBTI;
import com.avocado.statistics.db.mysql.repository.dto.ChartDistributionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MBTIDistributionResp {
    private String kind;
    private Long count;

    public static MBTIDistributionResp from(ChartDistributionDTO dto) {
        return MBTIDistributionResp.builder()
                .kind(MBTI.getMBTI(dto.getId()))
                .count(dto.getCount())
                .build();
    }

    @Builder
    public MBTIDistributionResp(String kind, Long count) {
        this.kind = kind;
        this.count = count;
    }
}
