package com.avocado.statistics.api.service;

import com.avocado.statistics.common.codes.ScoreFactor;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.MerchandiseIdSetRepository;
import com.avocado.statistics.db.redis.repository.ScoreRepository;
import com.avocado.statistics.kafka.dto.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.BitSet;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProviderDBService {

    private final ScoreRepository scoreRepository;
    private final MerchandiseIdSetRepository merchandiseIdSetRepository;
    private final ScoreFactor sc;

    // 매일 밤 12시 5분에 처리
    @Transactional
    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Seoul")
    public void update() {
        BitSet bitSet = merchandiseIdSetRepository.getBitSet();
        int bitSetLen = bitSet.length();

        for (long merchandiseId=0; merchandiseId < bitSetLen; merchandiseId++) {
            // 사용되지 않은 상품이면 지나치기
            if (!bitSet.get((int) merchandiseId)) {
                continue;
            }

            // ageGender - 14개
            Map<Integer, Long> ageGenderViewMap = scoreRepository.getMapByMerchandiseId(CategoryType.AGE_GENDER, Type.VIEW, merchandiseId);
            Map<Integer, Long> ageGenderClickMap = scoreRepository.getMapByMerchandiseId(CategoryType.AGE_GENDER, Type.CLICK, merchandiseId);
            Map<Integer, Long> ageGenderLikeMap = scoreRepository.getMapByMerchandiseId(CategoryType.AGE_GENDER, Type.LIKE, merchandiseId);
            Map<Integer, Long> ageGenderCartMap = scoreRepository.getMapByMerchandiseId(CategoryType.AGE_GENDER, Type.CART, merchandiseId);
            Map<Integer, Long> ageGenderPaymentMap = scoreRepository.getMapByMerchandiseId(CategoryType.AGE_GENDER, Type.PAYMENT, merchandiseId);

            for (int i = 0; i < 14; i++) {
                Long ageGenderView = ageGenderViewMap.getOrDefault(i, 0L);
                Long ageGenderClick = ageGenderClickMap.getOrDefault(i, 0L);
                Long ageGenderLike = ageGenderLikeMap.getOrDefault(i, 0L);
                Long ageGenderCart = ageGenderCartMap.getOrDefault(i, 0L);
                Long ageGenderPayment = ageGenderPaymentMap.getOrDefault(i, 0L);

                Long ageGenderScore = ageGenderView * sc.VIEW + ageGenderClick * sc.CLICK + ageGenderLike * sc.LIKE + ageGenderCart * sc.CART + ageGenderPayment * sc.PAY;

                int age = (i / 2 + 1) * 10;
                String gender;
                if (i % 2 == 0) {
                    gender = "F";
                } else {
                    gender = "M";
                }



            }



        }

    }



}
