package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.statistics.api.response.BasicConsumerStatisticsResp;
import com.avocado.statistics.api.response.ProductStatisticsDetailResp;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.mysql.repository.dto.CategoryDistributionDTO;
import com.avocado.statistics.db.mysql.repository.jpa.ConsumerStatisticsRepository;
import com.avocado.statistics.db.mysql.repository.mybatis.ConsumerRepository;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.ScoreRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerStatisticsService {
    private final ConsumerRepository consumerRepository;
    private final ScoreRepository scoreRepository;
    private final ConsumerStatisticsRepository consumerStatisticsRepository;

    private final JwtUtils jwtUtils;

    public ProductStatisticsDetailResp getDetailStatistics(long merchandiseId) {
        List<Long> ageGenderPayList = scoreRepository.getByMerchandiseId(CategoryType.AGE_GENDER, ActionType.PAYMENT, merchandiseId);
        List<Long> mbtiPayList = scoreRepository.getByMerchandiseId(CategoryType.MBTI, ActionType.PAYMENT, merchandiseId);
        List<Long> personalColorPayList = scoreRepository.getByMerchandiseId(CategoryType.PERSONAL_COLOR, ActionType.PAYMENT, merchandiseId);
        ProductStatisticsDetailResp resp = new ProductStatisticsDetailResp(ageGenderPayList, mbtiPayList, personalColorPayList);
        log.info("response: {}", resp);
        return resp;
    }

    /**
     * 구매자 사용자의 기본 통계 조회하기 (사용한 총액, 구매한 상품들의 카테고리 분포)
     */
    public BasicConsumerStatisticsResp getBasicStatistics(Claims claims) {
        UUID consumerId = jwtUtils.getId(claims);
        consumerRepository.getById(consumerId)
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."));

        Long totalMoneySpent = consumerStatisticsRepository.getTotalMoneySpent(consumerId);
        List<CategoryDistributionDTO> categories = consumerStatisticsRepository.getCategoryDistribution(consumerId);

        return BasicConsumerStatisticsResp.builder()
                .total_money(totalMoneySpent)
                .categoriesDTOs(categories)
                .build();
    }
}
