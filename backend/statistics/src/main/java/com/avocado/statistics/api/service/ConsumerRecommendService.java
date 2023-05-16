package com.avocado.statistics.api.service;

import com.avocado.ActionType;
import com.avocado.statistics.api.dto.ScoreResult;
import com.avocado.statistics.api.response.ConsumerRecommendResp;
import com.avocado.statistics.api.response.MerchandiseResp;
import com.avocado.statistics.common.codes.RecommendFactor;
import com.avocado.statistics.common.codes.ScoreFactor;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.common.utils.CategoryTypeUtil;
import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.mysql.entity.mybatis.Consumer;
import com.avocado.statistics.db.mysql.repository.dto.MerchandiseGroupDTO;
import com.avocado.statistics.db.mysql.repository.dto.MerchandiseMainDTO;
import com.avocado.statistics.db.mysql.repository.mybatis.ConsumerRepository;
import com.avocado.statistics.db.mysql.repository.mybatis.MerchandiseRepository;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.MerchandiseIdSetRepository;
import com.avocado.statistics.db.redis.repository.ScoreRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ConsumerRecommendService {

    private final ScoreFactor sc;
    private final RecommendFactor rf;
    private final JwtUtils jwtUtils;
    private final CategoryTypeUtil categoryTypeUtil;
    private final ScoreRepository scoreRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseIdSetRepository merchandiseIdSetRepository;
    private final ConsumerRepository consumerRepository;

    public ConsumerRecommendResp getConsumerRecommend(Claims claims) {
        UUID id = jwtUtils.getId(claims);
        Optional<Consumer> consumerO = consumerRepository.getById(id);
        if (consumerO.isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다.");
        }
        Consumer consumer = consumerO.get();
        List<CategoryType> consumerTypes = new ArrayList<>();
        List<CategoryType> mbtiType = new ArrayList<>();
        List<CategoryType> personalColorType = new ArrayList<>();

        if (consumer.getAge() != null && consumer.getGender() != null) {
            consumerTypes.add(CategoryType.AGE_GENDER);
        }

        if (consumer.getMbtiId() != null) {
            consumerTypes.add(CategoryType.MBTI);
            mbtiType.add(CategoryType.MBTI);
        }

        if (consumer.getPersonalColorId() != null) {
            consumerTypes.add(CategoryType.PERSONAL_COLOR);
            personalColorType.add(CategoryType.PERSONAL_COLOR);
        }

        BitSet bitSet = merchandiseIdSetRepository.getBitSet();

        List<MerchandiseResp> consumerRecommends = getMerchandiseList(consumer, consumerTypes, bitSet);
        System.out.println(consumerRecommends);
        List<MerchandiseResp> personalColorRecommends = getMerchandiseList(consumer, personalColorType, bitSet);
        System.out.println(personalColorRecommends);
        List<MerchandiseResp> mbtiRecommends = getMerchandiseList(consumer, mbtiType, bitSet);
        System.out.println(mbtiRecommends);

        return ConsumerRecommendResp.builder()
                .consumerRecommends(consumerRecommends)
                .personalColorRecommends(personalColorRecommends)
                .mbtiRecommends(mbtiRecommends).build();
    }

    public List<MerchandiseResp> getMerchandiseList(Consumer consumer, List<CategoryType> cTypes, BitSet bitset) {
        List<ScoreResult> scoreResults = calculateRecommend(consumer, cTypes, bitset);

        List<MerchandiseResp> respList = new LinkedList<>();
        Set<Long> idSet = new HashSet<>();
        
        // 추천 기준으로 넣은 물품들
        int i = 0;
        for (ScoreResult sc: scoreResults) {
            long merchandiseId = sc.getMerchandiseId();
            MerchandiseResp resp = merchandiseRespFrom(merchandiseId, consumer.getId());
            respList.add(resp);
            idSet.add(merchandiseId);
            i++;
            if (i == rf.MAX_RECOMMEND) {
                break;
            }
        }

        // 랜덤으로 넣은 물품들
        int randomLen = rf.TOTAL_SIZE - respList.size();
        i = 0;
        while (i < randomLen) {
            long merchandiseId = new Random().nextInt(31866);
            if (idSet.contains(merchandiseId)) {
                continue;
            }
            idSet.add(merchandiseId);
            MerchandiseResp resp = merchandiseRespFrom(merchandiseId, consumer.getId());
            if (respList.size() == 0) {
                respList.add(resp);
            } else {
                respList.add(new Random().nextInt(respList.size()), resp);
            }
            i++;
        }

        return respList;
    }

    private MerchandiseResp merchandiseRespFrom(long merchandiseId, UUID consumerId) {
        Optional<MerchandiseMainDTO> mainInfoO = merchandiseRepository.getInfoById(merchandiseId);
        if (mainInfoO.isEmpty()) {
            throw new BaseException(ResponseCode.INVALID_VALUE);
        }
        long groupId = mainInfoO.get().getGroupId();
        MerchandiseGroupDTO groupInfo = merchandiseRepository.getInfoByGroupId(groupId).get();

        String mbtiTag = merchandiseRepository.getMBTITag(merchandiseId);
        Integer ageGroup = merchandiseRepository.getAgeGroup(merchandiseId);
        String personalColorTag = merchandiseRepository.getPersonalColorTag(merchandiseId);

        Optional<Integer> wishlistExists = merchandiseRepository.wishlistExists(consumerId, merchandiseId);

        MerchandiseResp resp = new MerchandiseResp();
        resp.updateMerchandiseMainInfo(mainInfoO.get());
        resp.updateMerchandiseGroupInfo(groupInfo);
        resp.updateMBTITag(mbtiTag);
        resp.updateAgeGroupTag(ageGroup);
        resp.updatePersonalColorTag(personalColorTag);
        if (wishlistExists.isPresent()) {
            resp.updateWishlist(true);
        }

        return resp;
    }


    private List<ScoreResult> calculateRecommend(Consumer consumer, List<CategoryType> cTypes, BitSet bitSet) {
        int bitSetLen = bitSet.length();

        List<ScoreResult> scoreResults = new ArrayList<>();

        for (long merchandiseId = 0; merchandiseId < bitSetLen; merchandiseId++) {
            if (!bitSet.get((int) merchandiseId)) {
                continue;
            }
            Long score = 0L;
            for (CategoryType cType: cTypes) {
                Map<Integer, Long> viewMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.VIEW, merchandiseId);
                Map<Integer, Long> clickMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.CLICK, merchandiseId);
                Map<Integer, Long> likeMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.LIKE, merchandiseId);
                Map<Integer, Long> cartMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.CART, merchandiseId);
                Map<Integer, Long> paymentMap = scoreRepository.getMapByMerchandiseId(cType, ActionType.PAYMENT, merchandiseId);

                int index;
                switch (cType) {
                    case MBTI:
                        index = consumer.getMbtiId();
                        break;
                    case AGE_GENDER:
                        index = categoryTypeUtil.getIndexOfGenderAgeGroup(consumer.getAge(), consumer.getGender());
                        break;
                    case PERSONAL_COLOR:
                        index = consumer.getPersonalColorId();
                        break;
                    default:
                        throw new BaseException(ResponseCode.INVALID_VALUE);
                }

                Long view = viewMap.getOrDefault(index, 0L);
                Long click = clickMap.getOrDefault(index, 0L);
                Long like = likeMap.getOrDefault(index, 0L);
                Long cart = cartMap.getOrDefault(index, 0L);
                Long payment = paymentMap.getOrDefault(index, 0L);

                score += view * sc.VIEW + click * sc.CLICK + like * sc.LIKE + cart * sc.CART + payment * sc.PAY;

            }
            if (score.equals(0L)) {
                continue;
            }

            ScoreResult scoreResult = ScoreResult.builder()
                    .score(score).merchandiseId(merchandiseId).build();

            scoreResults.add(scoreResult);
        }
        Collections.sort(scoreResults);

        return scoreResults;
    }
}
