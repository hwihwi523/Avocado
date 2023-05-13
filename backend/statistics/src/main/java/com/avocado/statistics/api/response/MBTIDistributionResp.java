package com.avocado.statistics.api.response;

import com.avocado.statistics.common.enums.MBTI;
import com.avocado.statistics.common.enums.PersonalColor;
import com.avocado.statistics.db.mysql.repository.dto.ChartDistributionDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MBTIDistributionResp {
    private final String kind;
    private final Long count;

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
