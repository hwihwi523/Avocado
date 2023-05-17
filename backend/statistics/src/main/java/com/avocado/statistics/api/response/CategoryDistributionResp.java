package com.avocado.statistics.api.response;

import com.avocado.statistics.common.enums.Category;
import com.avocado.statistics.db.mysql.repository.dto.CategoryDistributionDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryDistributionResp {
    private final String category;
    private final Long count;

    @Builder
    public CategoryDistributionResp(CategoryDistributionDTO categoryDistributionDTO) {
        this.category = Category.getCategory(categoryDistributionDTO.getCategoryId());
        this.count = categoryDistributionDTO.getCount();
    }
}
