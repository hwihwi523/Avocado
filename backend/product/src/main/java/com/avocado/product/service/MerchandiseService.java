package com.avocado.product.service;

import com.avocado.product.dto.etc.MaxScoreDTO;
import com.avocado.product.dto.query.ScoreDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.response.PageResp;
import com.avocado.product.dto.response.SimpleMerchandiseResp;
import com.avocado.product.repository.MerchandiseRepository;
import com.avocado.product.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;

    // 대표 퍼스널컬러, MBTI, 나이대 등 개인화 정보 조회를 위한 repo
    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public PageResp showMerchandiseList_NoOffset(Short categoryId, String brandName,
                                               Long lastMerchandiseId, Integer size) {
        Page<SimpleMerchandiseDTO> result = merchandiseRepository
                .findByCategoryAndBrand_NoOffset(categoryId, brandName, lastMerchandiseId, PageRequest.ofSize(size));
        List<SimpleMerchandiseDTO> queryContent = result.getContent();

        // 상품 ID 취합
        List<Long> myContentsId = new ArrayList<>();
        queryContent.forEach((myContent) -> myContentsId.add(myContent.getMerchandiseId()));

        // IN 쿼리로 퍼스널컬러, MBTI, 나이대 각각 한 번에 조회
        List<ScoreDTO> personalColors = scoreRepository.findPersonalColors(myContentsId);
        List<ScoreDTO> mbtis = scoreRepository.findMbtis(myContentsId);
        List<ScoreDTO> ages = scoreRepository.findAges(myContentsId);

        // 최대 점수를 갖는 퍼스널컬러, MBTI, 나이대 구하기
        Map<Long, MaxScoreDTO> maxPersonalColors = getMaxScores(personalColors);
        Map<Long, MaxScoreDTO> maxMbtis = getMaxScores(mbtis);
        Map<Long, MaxScoreDTO> maxAges = getMaxScores(ages);

        // 응답용 DTO 생성
        List<SimpleMerchandiseResp> respContent = new ArrayList<>();
        for (SimpleMerchandiseDTO simpleMerchandiseDTO : queryContent) {
            // 데이터 조합
            SimpleMerchandiseResp combined = new SimpleMerchandiseResp(simpleMerchandiseDTO);
            Long merchandiseId = combined.getMerchandise_id();  // 상품 ID
            if (maxPersonalColors.get(merchandiseId) != null)
                combined.updatePersonalColor(maxPersonalColors.get(merchandiseId).getType());  // 대표 퍼스널컬러
            if (maxMbtis.get(merchandiseId) != null)
                combined.updateMBTI(maxMbtis.get(merchandiseId).getType());  // 대표 MBTI
            if (maxAges.get(merchandiseId) != null)
                combined.updateAgeGroup(maxAges.get(merchandiseId).getType());  // 대표 나이대
            respContent.add(combined);
        }

        // 마지막으로 조회한 ID
        Long newLastMerchandiseId = respContent.isEmpty()
                ? null
                : respContent.get(respContent.size() - 1).getMerchandise_id();

        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId);
    }

    /**
     * Map을 사용해 각 상품이 갖는 최댓값 DTO로 접근, 그리고 최댓값 확인 및 갱신 작업
     * @param scoreInfos : (상품 ID, 개인화 정보 Type, 점수) 리스트
     * @return : 각 상품마다 최대 점수를 갖는 Type을 저장한 Map
     */
    private Map<Long, MaxScoreDTO> getMaxScores(List<ScoreDTO> scoreInfos) {
        Map<Long, MaxScoreDTO> maxScores = new HashMap<>();
        scoreInfos.forEach((scoreInfo) -> {
            // 새로 등장한 상품일 경우 초기화
            if (!maxScores.containsKey(scoreInfo.getMerchandiseId()))
                maxScores.put(scoreInfo.getMerchandiseId(), new MaxScoreDTO());
            // 최댓값 계산 및 갱신
            MaxScoreDTO maxScoreDTO = maxScores.get(scoreInfo.getMerchandiseId());
            Long originScore = maxScoreDTO.getMaxScore();
            if (originScore == null || originScore < scoreInfo.getCount()) {
                maxScoreDTO.setMaxScore(scoreInfo.getCount());
                maxScoreDTO.setType(scoreInfo.getType());
            }
        });
        return maxScores;
    }

    // Offset 사용 버전.
//    @Transactional(readOnly = true)
//    public PageResp showMerchandiseList_Offset(Short categoryId, String brandName,
//                                               Integer page, Integer size) {
//        Page<SimpleMerchandiseDTO> result = merchandiseRepository
//                .findByCategoryAndBrand_Offset(categoryId, brandName, PageRequest.of(page, size));
//        List<SimpleMerchandiseDTO> queryContent = result.getContent();
//
//        // 상품 ID 취합
//        List<Long> myContentsId = new ArrayList<>();
//        queryContent.forEach((myContent) -> myContentsId.add(myContent.getMerchandiseId()));
//
//        // IN 쿼리로 퍼스널컬러, MBTI, 나이대 각각 한 번에 조회
//        List<ScoreDTO> personalColors = scoreRepository.findPersonalColors(myContentsId);
//        List<ScoreDTO> mbtis = scoreRepository.findMbtis(myContentsId);
//        List<ScoreDTO> ages = scoreRepository.findAges(myContentsId);
//
//        // 최대 점수를 갖는 퍼스널컬러, MBTI, 나이대 구하기
//        Map<Long, MaxScoreDTO> maxPersonalColors = getMaxScores(personalColors);
//        Map<Long, MaxScoreDTO> maxMbtis = getMaxScores(mbtis);
//        Map<Long, MaxScoreDTO> maxAges = getMaxScores(ages);
//
//        // 응답용 DTO 생성
//        List<SimpleMerchandiseResp> respContent = new ArrayList<>();
//        for (SimpleMerchandiseDTO simpleMerchandiseDTO : queryContent) {
//            // 데이터 조합
//            SimpleMerchandiseResp combined = new SimpleMerchandiseResp(simpleMerchandiseDTO);
//            Long merchandiseId = combined.getMerchandise_id();  // 상품 ID
//            if (maxPersonalColors.get(merchandiseId) != null)
//                combined.updatePersonalColor(maxPersonalColors.get(merchandiseId).getType());  // 대표 퍼스널컬러
//            if (maxMbtis.get(merchandiseId) != null)
//                combined.updateMBTI(maxMbtis.get(merchandiseId).getType());  // 대표 MBTI
//            if (maxAges.get(merchandiseId) != null)
//                combined.updateAgeGroup(maxAges.get(merchandiseId).getType());  // 대표 나이대
//            respContent.add(combined);
//        }
//
//        return PageResp.of(respContent, result.isLast());
//    }
}
