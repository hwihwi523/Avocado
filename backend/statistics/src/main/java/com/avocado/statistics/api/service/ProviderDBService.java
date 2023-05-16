package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.statistics.common.codes.ScoreFactor;
import com.avocado.statistics.common.utils.CategoryTypeUtil;
import com.avocado.statistics.api.dto.GenderAgeGroup;
import com.avocado.statistics.db.mysql.entity.mybatis.AgeGenderScoreMybatis;
import com.avocado.statistics.db.mysql.entity.mybatis.MbtiScoreMybatis;
import com.avocado.statistics.db.mysql.entity.mybatis.PersonalColorScoreMybatis;
import com.avocado.statistics.db.mysql.repository.mybatis.ScoreMybatisRepository;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.MerchandiseIdSetRepository;
import com.avocado.statistics.db.redis.repository.ScoreRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProviderDBService {

    private final ScoreRepository scoreRepository;
    private final ScoreMybatisRepository scoreMybatisRepository;
    private final MerchandiseIdSetRepository merchandiseIdSetRepository;
    private final CategoryTypeUtil categoryTypeUtil;
    private final ScoreFactor sc;

    // 매일 밤 12시 5분에 처리
    @Transactional
    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Seoul")
    public void update() {
        BitSet bitSet = merchandiseIdSetRepository.getBitSet();
        int bitSetLen = bitSet.length();
        // 이전 기록 전부 지우기
        scoreMybatisRepository.deleteAllMbti();
        scoreMybatisRepository.deleteAllAgeGender();
        scoreMybatisRepository.deleteAllPersonalColor();

        List<AgeGenderScoreMybatis> ageGenderScoreList = new ArrayList<>();
        List<MbtiScoreMybatis> mbtiScoreList = new ArrayList<>();
        List<PersonalColorScoreMybatis> personalColorScoreList = new ArrayList<>();

        for (long merchandiseId = 0; merchandiseId < bitSetLen; merchandiseId++) {
            // 사용되지 않은 상품이면 지나치기
            if (!bitSet.get((int) merchandiseId)) {
                continue;
            }

            // 종류별로 점수 저장하기
            for (CategoryType cType : CategoryType.values()) {

                Map<Integer, Long> viewMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.VIEW, merchandiseId);
                Map<Integer, Long> clickMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.CLICK, merchandiseId);
                Map<Integer, Long> likeMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.LIKE, merchandiseId);
                Map<Integer, Long> cartMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.CART, merchandiseId);
                Map<Integer, Long> paymentMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.PAYMENT, merchandiseId);

                int varSize = categoryTypeUtil.getVarSize(cType);
                for (int i = 0; i < varSize; i++) {
                    Long view = viewMap.getOrDefault(i, 0L);
                    Long click = clickMap.getOrDefault(i, 0L);
                    Long like = likeMap.getOrDefault(i, 0L);
                    Long cart = cartMap.getOrDefault(i, 0L);
                    Long payment = paymentMap.getOrDefault(i, 0L);

                    Long score = view * sc.VIEW + click * sc.CLICK + like * sc.LIKE + cart * sc.CART + payment * sc.PAY;

                    // 0점이면 저장 않고 넘어가기
                    if (score.equals(0L)) {
                        continue;
                    }

                    switch (cType) {
                        case AGE_GENDER:
                            GenderAgeGroup genderAgeGroup = categoryTypeUtil.getGenderAgeGroupFromIndex(i);
                            int age = genderAgeGroup.getAgeGroup();
                            String gender = genderAgeGroup.getGender();

                            AgeGenderScoreMybatis ageGenderScoreMybatis = AgeGenderScoreMybatis.builder()
                                    .score(score).age(age)
                                    .gender(gender).merchandiseId(merchandiseId).build();
                            ageGenderScoreList.add(ageGenderScoreMybatis);
                            break;
                        case MBTI:
                            MbtiScoreMybatis mbtiScoreMybatis = MbtiScoreMybatis.builder()
                                    .mbtiId(i).score(score).merchandiseId(merchandiseId).build();
                            mbtiScoreList.add(mbtiScoreMybatis);
                            break;
                        case PERSONAL_COLOR:
                            PersonalColorScoreMybatis personalColorScoreMybatis = PersonalColorScoreMybatis.builder()
                                    .score(score).personalColorId(i).merchandiseId(merchandiseId).build();
                            personalColorScoreList.add(personalColorScoreMybatis);
                            break;
                    }
                }
            }
        }

        scoreMybatisRepository.ageGenderBulkSave(ageGenderScoreList);
        scoreMybatisRepository.mbtiBulkSave(mbtiScoreList);
        scoreMybatisRepository.personalColorBulkSave(personalColorScoreList);
    }
}
