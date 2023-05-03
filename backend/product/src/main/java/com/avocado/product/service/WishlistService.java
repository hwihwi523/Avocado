package com.avocado.product.service;

import com.avocado.product.dto.etc.MaxScoreDTO;
import com.avocado.product.dto.query.ScoreDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.response.SimpleMerchandiseResp;
import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Merchandise;
import com.avocado.product.entity.Wishlist;
import com.avocado.product.exception.AccessDeniedException;
import com.avocado.product.exception.ErrorCode;
import com.avocado.product.exception.InvalidValueException;
import com.avocado.product.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final ConsumerRepository consumerRepository;

    // 퍼스널컬러, MBTI, 나이대 등 개인화 정보를 조회하기 위한 service
    private final ScoreService scoreService;

    @Transactional
    public void addProductToWishlist(Long merchandiseId, UUID consumerId) {
        // 기존 찜 내역 조회 (구매자 ID, 상품 ID로 조회)
        Wishlist wishlist = wishlistRepository.searchWishlist(null, consumerId, merchandiseId);
        if (wishlist != null)
            throw new InvalidValueException(ErrorCode.EXISTS_WISHLIST);

        // 물품 조회
        Merchandise merchandise = merchandiseRepository.findById(merchandiseId);
        if (merchandise == null)
            throw new InvalidValueException(ErrorCode.NO_MERCHANDISE);

        // 구매자 조회
        Consumer consumer = consumerRepository.findById(consumerId);
        if (consumer == null)
            throw new InvalidValueException(ErrorCode.NO_MEMBER);

        // 새로운 찜 내역 등록
        wishlist = Wishlist.builder()
                .merchandise(merchandise)
                .consumer(consumer)
                .build();
        wishlistRepository.save(wishlist);
    }

    /**
     * 장바구니 목록 조회
     * @param consumerId : 요청한 소비자의 ID
     * @return : 해당 소비자의 장바구니 목록
     */
    @Transactional(readOnly = true)
    public List<SimpleMerchandiseResp> showMyWishlist(UUID consumerId) {
        // 상품 정보 리스트 조회
        List<SimpleMerchandiseDTO> myWishlist = wishlistRepository.findMyWishlist(consumerId);
        return scoreService.insertPersonalInfoIntoList(myWishlist);
    }

    @Transactional
    public void removeProductFromWishList(UUID consumerId, Long wishlistId) {
        // 기존 찜 내역 조회 (찜 ID로 조회)
        Wishlist wishlist = wishlistRepository.searchWishlist(wishlistId, null, null);

        // 본인의 찜 목록이 아니라면 Forbidden 예외
        if (wishlist != null && !wishlist.getConsumer().getId().equals(consumerId))
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);

        // 찜 내역이 존재하지 않을 경우 InvalidValueException 반환
        // 본 예외를 먼저 확인하게 되면, 제3자가 불특정 타인의 찜 내역 존재 여부를 파악할 수 있음
        if (wishlist == null)
            throw new InvalidValueException(ErrorCode.NO_WISHLIST);

        // 삭제
        wishlistRepository.delete(wishlist);
    }
}
