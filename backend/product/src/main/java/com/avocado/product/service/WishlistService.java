package com.avocado.product.service;

import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.dto.query.WishlistMerchandiseDTO;
import com.avocado.product.dto.response.DefaultMerchandiseResp;
import com.avocado.product.dto.response.SimpleMerchandiseResp;
import com.avocado.product.dto.response.WishlistMerchandiseResp;
import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Merchandise;
import com.avocado.product.entity.Wishlist;
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

    @Transactional
    public void addProductToWishlist(Long merchandiseId, UUID consumerId) {
        // 기존 찜 내역 조회 (구매자 ID, 상품 ID로 조회)
        Wishlist wishlist = wishlistRepository.searchWishlist(consumerId, merchandiseId);
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
    public List<WishlistMerchandiseResp> showMyWishlist(UUID consumerId) {
        // 상품 정보 리스트 조회
        List<WishlistMerchandiseDTO> myWishlist = wishlistRepository.findMyWishlist(consumerId);

        List<WishlistMerchandiseResp> respContent = new ArrayList<>();
        for (WishlistMerchandiseDTO dto : myWishlist)
            respContent.add(new WishlistMerchandiseResp(dto));
        return respContent;
    }

    @Transactional
    public void removeProductFromWishList(UUID consumerId, Long merchandiseId) {
        // 기존 찜 내역 조회 (찜 ID로 조회)
        Wishlist wishlist = wishlistRepository.searchWishlist(consumerId, merchandiseId);

        // 찜 내역이 존재하지 않을 경우 InvalidValueException 반환
        if (wishlist == null)
            throw new InvalidValueException(ErrorCode.NO_WISHLIST);

        // 삭제
        wishlistRepository.delete(wishlist);
    }

    @Transactional(readOnly = true)
    public <T extends DefaultMerchandiseResp> void updateIsWishlist(UUID consumerId, List<T> respContent) {
        // 상품 ID 취합
        List<Long> merchandiseIds = new ArrayList<>();
        for (DefaultMerchandiseResp data : respContent)
            merchandiseIds.add(data.getMerchandise_id());

        // 구매자 ID, 상품 IDs로 찜꽁한 상품 ID 조회
        List<Long> interestedMerchandiseIds = wishlistRepository
                .findWishlistMerchandiseIds(consumerId, merchandiseIds);

        // 찜꽁한 상품의 is_wishlist를 true로 변경
        int wIdx = 0;
        for (DefaultMerchandiseResp resp : respContent) {
            // 찜꽁한 상품이 등장하면 is_wishlist를 true로 바꾸고, 다음 찜꽁 상품 ID로 넘어가기
            if (wIdx < interestedMerchandiseIds.size()
                    && resp.getMerchandise_id().equals(interestedMerchandiseIds.get(wIdx))) {
                resp.updateIsWishlist(true);
                wIdx++;
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean testExistsWishlist(UUID consumerId, String merchandiseName) {
        Long wishlistId = wishlistRepository.findByMerchandiseName(consumerId, merchandiseName);
        return wishlistId != null;
    }
}
