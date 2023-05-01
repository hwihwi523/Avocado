package com.avocado.product.service;

import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Merchandise;
import com.avocado.product.entity.Wishlist;
import com.avocado.product.exception.ErrorCode;
import com.avocado.product.exception.InvalidValueException;
import com.avocado.product.repository.ConsumerRepository;
import com.avocado.product.repository.MerchandiseRepository;
import com.avocado.product.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final ConsumerRepository consumerRepository;

    @Transactional
    public void addProductToWishlist(Long merchandiseId, UUID consumerId) {
        // 기존 찜 내역 조회
        Wishlist wishlist = wishlistRepository.searchWishlist(merchandiseId, consumerId);
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

    @Transactional
    public void removeProductFromWishList(Long merchandiseId, UUID consumerId) {
        // 기존 찜 내역 조회
        Wishlist wishlist = wishlistRepository.searchWishlist(merchandiseId, consumerId);
        if (wishlist == null)
            throw new InvalidValueException(ErrorCode.NO_WISHLIST);

        // 삭제
        wishlistRepository.delete(wishlist);
    }
}
