package com.avocado.statistics.api.service;

import com.avocado.statistics.api.response.ProductStatisticsDetailResp;
import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.ScoreRepository;
import com.avocado.statistics.kafka.dto.Type;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsumerStatisticsService {

    private final JwtUtils jwtUtils;
    private final ScoreRepository scoreRepository;


    public Object getConsumerRecommendPage(Claims claims) {

        UUID id = jwtUtils.getId(claims);
        String type = jwtUtils.getType(claims);


        return null;
    }

    public ProductStatisticsDetailResp getDetailStatistics(long merchandiseId) {
        List<Long> ageGenderPayList = scoreRepository.getByMerchandiseId(CategoryType.AGE_GENDER, Type.PAYMENT, merchandiseId);
        List<Long> mbtiPayList = scoreRepository.getByMerchandiseId(CategoryType.MBTI, Type.PAYMENT, merchandiseId);
        List<Long> personalColorPayList = scoreRepository.getByMerchandiseId(CategoryType.PERSONAL_COLOR, Type.PAYMENT, merchandiseId);
        ProductStatisticsDetailResp resp = new ProductStatisticsDetailResp(ageGenderPayList, mbtiPayList, personalColorPayList);
        return resp;
    }
}
