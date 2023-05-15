package com.avocado.statistics.api.service;

import com.avocado.statistics.api.response.ProductStatisticsDetailResp;
import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.ScoreRepository;
import com.avocado.statistics.kafka.dto.Type;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerStatisticsService {

    private final ScoreRepository scoreRepository;

    public ProductStatisticsDetailResp getDetailStatistics(long merchandiseId) {
        List<Long> ageGenderPayList = scoreRepository.getByMerchandiseId(CategoryType.AGE_GENDER, Type.PAYMENT, merchandiseId);
        log.info("ageGenderPayList: {}", ageGenderPayList);
        List<Long> mbtiPayList = scoreRepository.getByMerchandiseId(CategoryType.MBTI, Type.PAYMENT, merchandiseId);
        log.info("mbtiPayList: {}", mbtiPayList);
        List<Long> personalColorPayList = scoreRepository.getByMerchandiseId(CategoryType.PERSONAL_COLOR, Type.PAYMENT, merchandiseId);
        log.info("personalColorPayList: {}", personalColorPayList);
        ProductStatisticsDetailResp resp = new ProductStatisticsDetailResp(ageGenderPayList, mbtiPayList, personalColorPayList);
        log.info("response: {}", resp);
        return resp;
    }
}
