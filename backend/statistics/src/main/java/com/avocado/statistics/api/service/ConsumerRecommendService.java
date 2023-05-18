package com.avocado.statistics.api.service;

import com.avocado.statistics.api.dto.ScoreResult;
import com.avocado.statistics.api.response.ConsumerRecommendResp;
import com.avocado.statistics.api.response.MerchandiseResp;
import com.avocado.statistics.common.codes.RecommendFactor;
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

import com.avocado.statistics.db.redis.repository.ScoreConsumerRecommendRepository;
import com.avocado.statistics.db.redis.repository.ScoreTypeRecommendRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerRecommendService {

    private final JwtUtils jwtUtils;
    private final RecommendFactor rf;
    private final ConsumerRepository consumerRepository;
    private final CategoryTypeUtil categoryTypeUtil;
    private final MerchandiseRepository merchandiseRepository;
    private final ScoreConsumerRecommendRepository scoreConsumerRecommendRepository;
    private final ScoreTypeRecommendRepository scoreTypeRecommendRepository;

    public ConsumerRecommendResp getConsumerRecommend(Claims claims) {
        UUID id = jwtUtils.getId(claims);
        Optional<Consumer> consumerO = consumerRepository.getById(id);
        if (consumerO.isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다.");
        }
        Consumer consumer = consumerO.get();

        List<MerchandiseResp> consumerRecommends = new ArrayList<>();
        List<MerchandiseResp> personalColorRecommends = new ArrayList<>();
        List<MerchandiseResp> mbtiRecommends = new ArrayList<>();

        Integer personalColorId = consumer.getPersonalColorId();
        Integer mbtiId = consumer.getMbtiId();
        Integer age = consumer.getAge();
        String gender = consumer.getGender();

        if (personalColorId != null) {
            List<ScoreResult> scoreResults = getCategoryTypeScoreResults(personalColorId, CategoryType.PERSONAL_COLOR);
            personalColorRecommends.addAll(getMerchandiseList(scoreResults, id));
        }


        if (mbtiId != null) {
            List<ScoreResult> scoreResults = getCategoryTypeScoreResults(mbtiId, CategoryType.MBTI);
            mbtiRecommends.addAll(getMerchandiseList(scoreResults, id));
        }

        if (personalColorId != null && mbtiId != null) {
            List<ScoreResult> scoreResults = getConsumerScoreResults(consumer);
            consumerRecommends.addAll(getMerchandiseList(scoreResults, id));
        } else if (consumer.getAge() != null && consumer.getGender() != null) {
            List<ScoreResult> scoreResults = getCategoryTypeScoreResults(categoryTypeUtil.getIndexOfGenderAgeGroup(age, gender), CategoryType.AGE_GENDER);
            consumerRecommends.addAll(getMerchandiseList(scoreResults, id));
        }

        return ConsumerRecommendResp.builder()
                .consumerRecommends(consumerRecommends)
                .personalColorRecommends(personalColorRecommends)
                .mbtiRecommends(mbtiRecommends).build();
    }

    public List<ScoreResult> getConsumerScoreResults(Consumer consumer) {
        List<ScoreResult> scoreResults = new ArrayList<>();

        Integer mbtiId = consumer.getMbtiId();
        Integer personalColorId = consumer.getPersonalColorId();
        String gender = consumer.getGender();
        Integer age = consumer.getAge();
        int ageGenderIndex = categoryTypeUtil.getIndexOfGenderAgeGroup(age, gender);
        Map<Long, Long> map = scoreConsumerRecommendRepository.getMap(ageGenderIndex, personalColorId, mbtiId);
        for (Long merchandiseId: map.keySet()) {
            Long score = map.get(merchandiseId);
            scoreResults.add(new ScoreResult(merchandiseId, score));
        }
        Collections.sort(scoreResults);
        return scoreResults;
    }

    public List<ScoreResult> getCategoryTypeScoreResults(int index, CategoryType cType) {
        List<ScoreResult> scoreResults = new ArrayList<>();
        Map<Long, Long> map = scoreTypeRecommendRepository.getMap(cType, index);
        for (Long merchandiseId: map.keySet()) {
            Long score = map.get(merchandiseId);
            scoreResults.add(new ScoreResult(merchandiseId, score));
        }
        Collections.sort(scoreResults);
        return scoreResults;
    }

    public List<MerchandiseResp> getMerchandiseList(List<ScoreResult> scoreResults, UUID id) {

        List<MerchandiseResp> respList = new LinkedList<>();
        Set<Long> idSet = new HashSet<>();
        for (ScoreResult sc:scoreResults) {
            idSet.add(sc.getMerchandiseId());
        }
        int i = 0;
        for (ScoreResult sc: scoreResults) {
            long merchandiseId = sc.getMerchandiseId();
            MerchandiseResp resp = merchandiseRespFrom(merchandiseId, id);
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
            MerchandiseResp resp = merchandiseRespFrom(merchandiseId, id);
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


}
