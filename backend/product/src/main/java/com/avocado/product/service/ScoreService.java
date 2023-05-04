package com.avocado.product.service;

import com.avocado.product.dto.etc.MaxScoreDTO;
import com.avocado.product.dto.query.*;
import com.avocado.product.dto.response.*;
import com.avocado.product.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    /**
     * DB 조회한 데이터에 해당 상품의 대표 퍼스널컬러, MBTI, 나이대를 부착하며
     * Response DTO로 변환하는 메서드
     * @param queryContent : DB 조회 데이터
     * @return : Response DTO (DB 조회 데이터 + 대표 퍼스널컬러, MBTI, 나이대)
     */
    @Transactional(readOnly = true)
    public <Param extends DefaultMerchandiseDTO,
            Return extends DefaultMerchandiseResp> List<Return> insertPersonalInfoIntoList(List<Param> queryContent,
                                                                                           Class<Return> returnClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 상품 ID 취합
        List<Long> myContentIds = new ArrayList<>();
        queryContent.forEach((myContent) -> myContentIds.add(myContent.getMerchandiseId()));

        // IN 쿼리로 퍼스널컬러, MBTI, 나이대 각각 한 번에 조회
        List<ScoreDTO> personalColors = scoreRepository.findPersonalColors(myContentIds);
        List<ScoreDTO> mbtis = scoreRepository.findMbtis(myContentIds);
        List<ScoreDTO> ages = scoreRepository.findAges(myContentIds);

        // 최대 점수를 갖는 퍼스널컬러, MBTI, 나이대 구하기
        Map<Long, MaxScoreDTO> maxPersonalColors = getMaxScores(personalColors);
        Map<Long, MaxScoreDTO> maxMbtis = getMaxScores(mbtis);
        Map<Long, MaxScoreDTO> maxAges = getMaxScores(ages);

        // 응답용 DTO 생성
        List<Return> respContent = new ArrayList<>();
        for (Param merchandiseDTO : queryContent) {
            // 데이터 조합
            Return combined = returnClass.getDeclaredConstructor().newInstance();

            // 클래스의 종류에 따라 데이터를 다르게 초기화
            if (combined instanceof CartMerchandiseResp) {
                ((CartMerchandiseResp) combined).updateCart((CartMerchandiseDTO) merchandiseDTO);
            } else if (combined instanceof WishlistMerchandiseResp) {
                ((WishlistMerchandiseResp) combined).updateWishlist((WishlistMerchandiseDTO) merchandiseDTO);
            } else if (combined instanceof SimpleMerchandiseResp) {
                ((SimpleMerchandiseResp) combined).updateSimple((SimpleMerchandiseDTO) merchandiseDTO);
            } else if (combined instanceof DetailMerchandiseResp) {
                ((DetailMerchandiseResp) combined).updateDetail((DetailMerchandiseDTO) merchandiseDTO);
            } else if (combined instanceof PurchaseHistoryMerchandiseResp) {
                ((PurchaseHistoryMerchandiseResp) combined).updatePurchaseHistory((PurchaseHistoryMerchandiseDTO) merchandiseDTO);
            } else if (combined instanceof ClickMerchandiseResp) {
                ((ClickMerchandiseResp) combined).updateClick((ClickMerchandiseDTO) merchandiseDTO);
            }

            Long merchandiseId = combined.getMerchandise_id();  // 상품 ID
            if (maxPersonalColors.get(merchandiseId) != null)
                combined.updatePersonalColor(maxPersonalColors.get(merchandiseId).getType());  // 대표 퍼스널컬러
            if (maxMbtis.get(merchandiseId) != null)
                combined.updateMBTI(maxMbtis.get(merchandiseId).getType());  // 대표 MBTI
            if (maxAges.get(merchandiseId) != null)
                combined.updateAgeGroup(maxAges.get(merchandiseId).getType());  // 대표 나이대
            respContent.add(combined);
        }

        return respContent;
    }

    @Transactional(readOnly = true)
    public DetailMerchandiseResp insertPersonalInfo(DetailMerchandiseDTO queryContent) {
        DetailMerchandiseResp respContent = new DetailMerchandiseResp(queryContent);

        // 대표 퍼스널컬러, MBTI, 나이대 부착
        String personalColor = scoreRepository.findPersonalColor(queryContent.getMerchandiseId());
        if (personalColor != null)
            respContent.updatePersonalColor(personalColor);

        String mbti = scoreRepository.findMbti(queryContent.getMerchandiseId());
        if (mbti != null)
            respContent.updateMBTI(mbti);

        String age = scoreRepository.findAge(queryContent.getMerchandiseId());
        if (age != null)
            respContent.updateAgeGroup(age);

        return respContent;
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
}
