package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.statistics.api.dto.ScoreResult;
import com.avocado.statistics.common.codes.RecommendFactor;
import com.avocado.statistics.common.codes.ScoreFactor;
import com.avocado.statistics.common.enums.Category;
import com.avocado.statistics.common.utils.CategoryTypeUtil;
import com.avocado.statistics.db.redis.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendCalculateService {

    private final ScoreFactor sc;
    private final ScoreRepository scoreRepository;
    private final CategoryTypeUtil categoryTypeUtil;
    private final MerchandiseIdSetRepository merchandiseIdSetRepository;
    private final ScoreTypeRecommendRepository scoreTypeRecommendRepository;
    private final ScoreConsumerRecommendRepository scoreConsumerRecommendRepository;


    @Scheduled(cron = "0 15 0 * * *", zone = "Asia/Seoul")
    public void calculateRecommend() {
        scoreConsumerRecommendRepository.deleteAll();
        scoreTypeRecommendRepository.deleteAll();

        long l1 = System.currentTimeMillis();
        calculateCategoryTypeRecommend(CategoryType.PERSONAL_COLOR);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);
        calculateCategoryTypeRecommend(CategoryType.MBTI);
        long l3 = System.currentTimeMillis();
        System.out.println(l3 - l2);
        calculateCategoryTypeRecommend(CategoryType.AGE_GENDER);
        long l4 = System.currentTimeMillis();
        System.out.println(l4 - l3);
        calculateConsumerRecommend();
        long l5 = System.currentTimeMillis();
        System.out.println(l5 - l4);
    }

    private void calculateCategoryTypeRecommend(CategoryType cType) {
        BitSet bitSet = merchandiseIdSetRepository.getBitSet();
        int bitSetLen = bitSet.length();
        int varSize = categoryTypeUtil.getVarSize(cType);
        for (int index = 0; index < varSize; index++) {

            for (long merchandiseId = 0; merchandiseId < bitSetLen; merchandiseId++) {
                if (!bitSet.get((int) merchandiseId)) {
                    continue;
                }
                Map<Integer, Long> viewMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.VIEW, merchandiseId);
                Map<Integer, Long> clickMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.CLICK, merchandiseId);
                Map<Integer, Long> likeMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.LIKE, merchandiseId);
                Map<Integer, Long> cartMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.CART, merchandiseId);
                Map<Integer, Long> paymentMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.PAYMENT, merchandiseId);

                Long view = viewMap.getOrDefault(index, 0L);
                Long click = clickMap.getOrDefault(index, 0L);
                Long like = likeMap.getOrDefault(index, 0L);
                Long cart = cartMap.getOrDefault(index, 0L);
                Long payment = paymentMap.getOrDefault(index, 0L);
                Long score = view * sc.VIEW + click * sc.CLICK + like * sc.LIKE + cart * sc.CART + payment * sc.PAY;
                if (score == 0L) {
                    continue;
                }
                scoreTypeRecommendRepository.save(cType, index, merchandiseId, score);

            }
        }
    }

    private void calculateConsumerRecommend() {
        BitSet bitSet = merchandiseIdSetRepository.getBitSet();
        int bitSetLen = bitSet.length();
        log.info("bitSetLen: {}", bitSetLen);

        for (long merchandiseId = 0; merchandiseId < bitSetLen; merchandiseId++) {
            log.info("merchandiseId: {}", merchandiseId);
            if (!bitSet.get((int) merchandiseId)) {
                continue;
            }
            for (int ageGenderIndex = 0; ageGenderIndex < categoryTypeUtil.getVarSize(CategoryType.AGE_GENDER); ageGenderIndex++) {
                Map<Long, Long> ageGenderMap = scoreTypeRecommendRepository.getMap(CategoryType.AGE_GENDER, ageGenderIndex);

                for (int mbtiId = 0; mbtiId < categoryTypeUtil.getVarSize(CategoryType.MBTI); mbtiId++) {
                    Map<Long, Long> mbtiMap = scoreTypeRecommendRepository.getMap(CategoryType.MBTI, mbtiId);

                    for (int personalColorId = 0; personalColorId < categoryTypeUtil.getVarSize(CategoryType.PERSONAL_COLOR); personalColorId++) {
                        Map<Long, Long> personalColorMap = scoreTypeRecommendRepository.getMap(CategoryType.PERSONAL_COLOR, personalColorId);
                        long score = ageGenderMap.getOrDefault(merchandiseId, 0L);
                        score += mbtiMap.getOrDefault(merchandiseId, 0L);
                        score += personalColorMap.getOrDefault(merchandiseId, 0L);
                        if (score == 0L) {
                            continue;
                        }
                        scoreConsumerRecommendRepository.save(ageGenderIndex, personalColorId, mbtiId, merchandiseId, score);
                    }
                }
            }
        }
    }
}
