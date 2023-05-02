package com.avocado.product.service;

import com.avocado.product.dto.etc.MaxScoreDTO;
import com.avocado.product.dto.query.ScoreDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.response.SimpleMerchandiseResp;
import com.avocado.product.entity.Cart;
import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Merchandise;
import com.avocado.product.exception.AccessDeniedException;
import com.avocado.product.exception.ErrorCode;
import com.avocado.product.exception.InvalidValueException;
import com.avocado.product.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ConsumerRepository consumerRepository;
    private final MerchandiseRepository merchandiseRepository;

    // 대표 퍼스널컬러, MBTI, 나이대 조회용 repo
    private final ScoreRepository scoreRepository;

    /**
     * 장바구니 내역 등록
     * @param consumerId : 요청한 소비자의 ID
     * @param productId : 등록할 상품의 ID
     */
    @Transactional
    public void addProductToCart(UUID consumerId, Long productId) {
        // 등록할 상품 Entity 찾기
        Merchandise merchandise = merchandiseRepository.findById(productId);
        if (merchandise == null)
            throw new InvalidValueException(ErrorCode.NO_MERCHANDISE);

        // 요청한 소비자 Entity 찾기
        Consumer consumer = consumerRepository.findById(consumerId);
        if (consumer == null)
            throw new InvalidValueException(ErrorCode.NO_MEMBER);

        // Entity 생성 및 저장
        Cart cart = Cart.builder()
                .consumer(consumer)
                .merchandise(merchandise)
                .build();
        cartRepository.save(cart);
    }

    /**
     * 장바구니 목록 조회
     * @param consumerId : 요청한 소비자의 ID
     * @return : 해당 소비자의 장바구니 목록
     */
    @Transactional(readOnly = true)
    public List<SimpleMerchandiseResp> showMyCart(UUID consumerId) {
        // 상품 정보 리스트
        List<SimpleMerchandiseDTO> myCart = cartRepository.findMyCart(consumerId);

        // 상품 ID 취합
        List<Long> myCartIds = new ArrayList<>();
        myCart.forEach((cart) -> myCartIds.add(cart.getMerchandise_id()));

        // IN 쿼리로 퍼스널컬러, MBTI, 나이대 각각 한 번에 조회
        List<ScoreDTO> personalColors = scoreRepository.findPersonalColors(myCartIds);
        List<ScoreDTO> mbtis = scoreRepository.findMbtis(myCartIds);
        List<ScoreDTO> ages = scoreRepository.findAges(myCartIds);

        // 최대 점수를 갖는 퍼스널컬러, MBTI, 나이대 구하기
        Map<Long, MaxScoreDTO> maxPersonalColors = getMaxScores(personalColors);
        Map<Long, MaxScoreDTO> maxMbtis = getMaxScores(mbtis);
        Map<Long, MaxScoreDTO> maxAges = getMaxScores(ages);

        // 반환용 DTO 생성
        List<SimpleMerchandiseResp> results = new ArrayList<>();
        for (SimpleMerchandiseDTO simpleMerchandiseDTO : myCart) {
            // 데이터 조합
            SimpleMerchandiseResp result = new SimpleMerchandiseResp(simpleMerchandiseDTO);
            Long merchandiseId = result.getMerchandise_id();  // 상품 ID
            if (maxPersonalColors.get(merchandiseId) != null)
                result.updatePersonalColor(maxPersonalColors.get(merchandiseId).getType());  // 대표 퍼스널컬러
            if (maxMbtis.get(merchandiseId) != null)
                result.updateMBTI(maxMbtis.get(merchandiseId).getType());  // 대표 MBTI
            if (maxAges.get(merchandiseId) != null)
                result.updateAgeGroup(maxAges.get(merchandiseId).getType());  // 대표 나이대
            results.add(result);
        }

        return results;
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

    /**
     * 장바구니 내역 삭제
     * @param consumerId : 요청한 소비자의 ID
     * @param cartId : 삭제 요청한 장바구니 ID
     */
    @Transactional
    public void removeProductFromCart(UUID consumerId, Long cartId) {
        // 장바구니 내역 찾기
        Cart cart = cartRepository.findByConsumerIdAndMerchandiseId(cartId);

        // 본인의 장바구니가 아니라면 Forbidden 예외
        if (cart != null && !cart.getConsumer().getId().equals(consumerId))
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);

        // 장바구니가 존재하지 않을 경우 InvalidValueException 반환
        // 본 예외를 먼저 확인하게 되면, 제3자가 불특정 타인의 장바구니 존재 여부를 파악할 수 있음
        if (cart == null)
            throw new InvalidValueException(ErrorCode.NO_CART);

        // 삭제
        cartRepository.delete(cart);
    }
}
