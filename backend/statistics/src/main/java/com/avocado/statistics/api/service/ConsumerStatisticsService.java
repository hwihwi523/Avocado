package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.statistics.api.response.ProductStatisticsDetailResp;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.ScoreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerStatisticsService {

    private final ScoreRepository scoreRepository;

    public ProductStatisticsDetailResp getDetailStatistics(long merchandiseId) {
        List<Long> ageGenderPayList = scoreRepository.getByMerchandiseId(CategoryType.AGE_GENDER, ActionType.PAYMENT, merchandiseId);
        List<Long> mbtiPayList = scoreRepository.getByMerchandiseId(CategoryType.MBTI, ActionType.PAYMENT, merchandiseId);
        List<Long> personalColorPayList = scoreRepository.getByMerchandiseId(CategoryType.PERSONAL_COLOR, ActionType.PAYMENT, merchandiseId);
        ProductStatisticsDetailResp resp = new ProductStatisticsDetailResp(ageGenderPayList, mbtiPayList, personalColorPayList);
        log.info("response: {}", resp);
        return resp;
    }
}
