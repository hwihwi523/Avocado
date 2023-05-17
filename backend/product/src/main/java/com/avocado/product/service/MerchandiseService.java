package com.avocado.product.service;

import com.avocado.product.dto.query.ClickMerchandiseDTO;
import com.avocado.product.dto.query.DetailMerchandiseDTO;
import com.avocado.product.dto.query.PurchaseHistoryMerchandiseDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.response.*;
import com.avocado.product.entity.Click;
import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Merchandise;
import com.avocado.product.entity.Wishlist;
import com.avocado.product.kafka.service.KafkaProducer;
import com.avocado.product.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final ConsumerRepository consumerRepository;
    private final ClickRepository clickRepository;
    private final PurchaseRepository purchaseRepository;  // 구매 여부 조회하기 위한 repo
    private final ReviewRepository reviewRepository;  // 리뷰 여부 조회하기 위한 repo
    private final WishlistRepository wishlistRepository;  // 찜꽁 여부를 조회하기 위한 repo

    // 대표 퍼스널컬러, MBTI, 나이대 등 개인화 정보 조회를 위한 service
    private final ScoreService scoreService;
    // is_wishlist 업데이트를 위한 service
    private final WishlistService wishlistService;

    private final KafkaProducer kafkaProducer;

    /**
     * 상품 목록을 카테고리와 브랜드 이름으로 조회한 데이터를 제공하는 서비스
     * @param categoryId : 카테고리 ID
     * @param providerId : 스토어 ID
     * @param lastMerchandiseId : 마지막으로 조회한 상품 ID
     * @param size : 한 번에 조회할 데이터의 개수
     * @return : 상품 목록
     */
    @Transactional(readOnly = true)
    public PageResp showMerchandiseList_NoOffset(UUID consumerId, Short categoryId, UUID providerId,
                                               Long lastMerchandiseId, Integer size) {
        // DB 조회
        Page<SimpleMerchandiseDTO> result = merchandiseRepository
                .findByCategoryAndBrand_NoOffset(categoryId, providerId, lastMerchandiseId, PageRequest.ofSize(size));

        // DTO -> Response 변환
        List<SimpleMerchandiseResp> respContent;

        try {
            respContent = scoreService.insertPersonalInfoIntoList(result.getContent(), SimpleMerchandiseResp.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        // 사용자가 존재할 경우 찜꽁 여부 조회 및 부착
        if (consumerId != null)
            wishlistService.updateIsWishlist(consumerId, respContent);

        // 마지막으로 조회한 ID
        Long newLastMerchandiseId = respContent.isEmpty()
                ? null
                : respContent.get(respContent.size() - 1).getMerchandise_id();

        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId, null);
    }

    /**
     * 특정 상품의 상세정보 조회하기
     * @param merchandiseId : 상품 ID
     * @return : 해당 상품의 상세정보, 요청한 사용자가 해당 상품을 구매했는지, 리뷰를 남겼는지 true / false
     */
    @Transactional(readOnly = true)
    public DetailMerchandiseResp showDetailMerchandise(UUID consumerId, Long merchandiseId) {
        // DB 조회
        DetailMerchandiseDTO queryContent = merchandiseRepository.findDetailMerchandise(merchandiseId);

        // 대표 퍼스널컬러, MBTI, 나이대 정보 부착
        DetailMerchandiseResp respContent = scoreService.insertPersonalInfo(queryContent);

        // 추가 이미지 조회 및 부착
        List<String> additionalImages = merchandiseRepository.findAdditionalImages(merchandiseId);
        respContent.updateImages(additionalImages);

        // 요청한 사용자가 있다면 구매 여부, 리뷰 여부 구하기
        if (consumerId != null) {
            // 요청한 사용자가 이 상품을 구매했는지 확인
            Boolean isPurchased = purchaseRepository.checkPurchased(consumerId, merchandiseId);
            respContent.updateIsPurchased(isPurchased);

            // 요청한 사용자가 이 상품을 구매했다면, 리뷰도 남겼는지 확인
            if (isPurchased) {
                Boolean isReviewed = reviewRepository.checkReviewed(consumerId, merchandiseId);
                respContent.updateIsReviewed(isReviewed);
            }

            // 찜꽁 여부 조회
            Wishlist wishlist = wishlistRepository.searchWishlist(consumerId, merchandiseId);
            respContent.updateIsWishlist(wishlist != null);
        }

        kafkaProducer.sendClick(merchandiseId, consumerId);

        return respContent;
    }
    @Transactional
    public void addClick(UUID consumerId, Long merchandiseId) {
        // 최근 본 물품 조회를 위해 조회 데이터 추가
        Consumer proxyConsumer = consumerRepository.getOne(consumerId);
        Merchandise proxyMerchandise = merchandiseRepository.getOne(merchandiseId);
        Click click = Click.builder()
                .consumer(proxyConsumer)
                .merchandise(proxyMerchandise)
                .build();
        clickRepository.save(click);
    }

    /**
     * 최근 본 상품 조회하기
     * @param consumerId : 사용자 ID
     * @return : 해당 사용자가 최근 조회한 상품 목록
     */
    @Transactional(readOnly = true)
    public List<ClickMerchandiseResp> showRecentMerchandises(UUID consumerId) {
        // DB 조회
        List<ClickMerchandiseDTO> recentMerchandises = merchandiseRepository.findRecentMerchandises(consumerId);

        // 대표 퍼스널컬러, MBTI, 나이대 추가 + DTO -> Response 변환
        List<ClickMerchandiseResp> respContent;
        try {
            respContent = scoreService.insertPersonalInfoIntoList(recentMerchandises, ClickMerchandiseResp.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        // 사용자가 존재할 경우 찜꽁 여부 조회 및 부착
        if (consumerId != null)
            wishlistService.updateIsWishlist(consumerId, respContent);

        return respContent;
    }

    /**
     * 구매내역 조회 서비스
     * @param consumerId : 사용자 ID
     * @param lastPurchaseDate : 마지막으로 조회한 구매내역의 시간
     * @param size : 조회할 데이터 개수
     * @return : 구매내역 리스트
     */
    @Transactional(readOnly = true)
    public PageResp showPurchaseMerchandises(UUID consumerId, LocalDateTime lastPurchaseDate, Integer size) {
        // DB 조회
        Page<PurchaseHistoryMerchandiseDTO> result = purchaseRepository
                .findPurchaseHistories(consumerId, lastPurchaseDate, size);

        // DTO -> Response 변환
        List<PurchaseHistoryMerchandiseResp> respContent;
        try {
            respContent = scoreService.insertPersonalInfoIntoList(
                    result.getContent(), PurchaseHistoryMerchandiseResp.class
            );
        } catch (Exception e) {
            throw new RuntimeException();
        }

        // 사용자가 존재할 경우 찜꽁 여부 조회 및 부착
        if (consumerId != null)
            wishlistService.updateIsWishlist(consumerId, respContent);

        // 마지막 상품의 구매일자
        LocalDateTime newLastMerchandiseId = respContent.isEmpty()
                ? null
                : respContent.get(respContent.size() - 1).getPurchase_date();

        return PageResp.of(respContent, result.isLast(), null, newLastMerchandiseId);
    }

    /**
     * 상품 인기순으로 조회하기
     * @param lastMerchandiseId : for pagination
     * @param size : 조회할 데이터 개수
     * @return : 인기순으로 조회한 상품
     */
    public PageResp showPopularMerchandises_NoOffset(Long lastMerchandiseId, Integer size) {
        // DB 조회
        Page<SimpleMerchandiseDTO> result = merchandiseRepository
                .findPopularMerchandises_NoOffset(lastMerchandiseId, PageRequest.ofSize(size));

        // DTO -> Response 변환
        List<SimpleMerchandiseResp> respContent;

        try {
            respContent = scoreService.insertPersonalInfoIntoList(result.getContent(), SimpleMerchandiseResp.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        // 마지막으로 조회한 ID
        Long newLastMerchandiseId = respContent.isEmpty()
                ? null
                : respContent.get(respContent.size() - 1).getMerchandise_id();

        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId, null);
    }

    // Offset 사용 버전.
//    @Transactional(readOnly = true)
//    public PageResp showMerchandiseList_Offset(Short categoryId, String brandName,
//                                               Integer page, Integer size) {
//        Page<SimpleMerchandiseDTO> result = merchandiseRepository
//                .findByCategoryAndBrand_Offset(categoryId, brandName, PageRequest.of(page, size));
//
//        // DTO -> Response 변환
//        List<SimpleMerchandiseResp> respContent = scoreService.appendPersonalInfo(
//                result.getContent()
//        );
//
//        // 마지막으로 조회한 ID
//        Long newLastMerchandiseId = respContent.isEmpty()
//                ? null
//                : respContent.get(respContent.size() - 1).getMerchandise_id();
//
//        return PageResp.of(respContent, result.isLast(), newLastMerchandiseId);
//    }
}
