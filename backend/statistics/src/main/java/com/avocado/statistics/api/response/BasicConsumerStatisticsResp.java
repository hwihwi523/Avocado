package com.avocado.statistics.api.response;

import com.avocado.statistics.db.mysql.repository.dto.CategoryDistributionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BasicConsumerStatisticsResp {
    private Long total_money;
    private List<CategoryDistributionResp> categories;

    @Builder
    public BasicConsumerStatisticsResp(Long total_money, List<CategoryDistributionDTO> categoriesDTOs) {
        this.total_money = total_money;

        this.categories = new ArrayList<>();
        for (CategoryDistributionDTO data : categoriesDTOs) {
            this.categories.add(
                    CategoryDistributionResp.builder()
                            .categoryDistributionDTO(data)
                            .build()
            );
        }
    }
}
