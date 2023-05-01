package com.avocado.product.service;

import com.avocado.product.dto.query.CartDTO;
import com.avocado.product.entity.Cart;
import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Merchandise;
import com.avocado.product.exception.AccessDeniedException;
import com.avocado.product.exception.ErrorCode;
import com.avocado.product.exception.InvalidValueException;
import com.avocado.product.repository.CartRepository;
import com.avocado.product.repository.ConsumerRepository;
import com.avocado.product.repository.MerchandiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ConsumerRepository consumerRepository;
    private final MerchandiseRepository merchandiseRepository;

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
    public List<CartDTO> showMyCart(UUID consumerId) {
        return cartRepository.findMyCart(consumerId);
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
        // 본 예외를 먼저 확인하게 되면, 제3자가 불특정 타인의 장바구니 존재 유무를 파악할 수 있음
        if (cart == null)
            throw new InvalidValueException(ErrorCode.NO_CART);

        // 삭제
        cartRepository.delete(cart);
    }
}
