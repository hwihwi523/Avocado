package com.avocado.product.repository;

import com.avocado.product.dto.query.CartMerchandiseDTO;
import com.avocado.product.dto.query.QCartMerchandiseDTO;
import com.avocado.product.entity.*;
import com.avocado.product.exception.DataManipulationException;
import com.avocado.product.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

import static com.avocado.product.entity.QCart.cart;
import static com.avocado.product.entity.QConsumer.consumer;
import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QMerchandiseCategory.merchandiseCategory;
import static com.avocado.product.entity.QMerchandiseGroup.merchandiseGroup;
import static com.avocado.product.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    /**
     * 생성 및 삭제
     */
    public void save(Cart cart) {
        try {
            em.persist(cart);
        } catch (PersistenceException e) {
            throw new DataManipulationException(ErrorCode.INVALID_INSERT);
        }
    }
    public void delete(Cart cart) {
        em.remove(cart);
    }

    /**
     * 장바구니 내역 단건 조회
     * @param cartId : 장바구니 ID
     * @return : 장바구니 단건 내역
     */
    public Cart findById(Long cartId) {
        return queryFactory
                .selectFrom(cart)
                .where(
                        cart.id.eq(cartId)
                )
                .fetchFirst();
    }
    public Cart findByConsumerIdAndMerchandiseId(UUID consumerId, Long merchandiseId, String size) {
        return queryFactory
                .selectFrom(cart)
                .join(cart.consumer, consumer).fetchJoin()
                .join(cart.merchandise, merchandise).fetchJoin()
                .where(
                        consumer.id.eq(consumerId),
                        merchandise.id.eq(merchandiseId),
                        cart.size.eq(size)
                )
                .fetchFirst();
    }

    /**
     * 특정 사용자의 장바구니 목록을 장바구니 ID 내림차순으로 정렬하여 조회
     * @param consumerId : 소비자 ID
     * @return : 조회 데이터
     */
    public List<CartMerchandiseDTO> findMyCart(UUID consumerId) {
        return queryFactory
                .select(new QCartMerchandiseDTO(
                        cart.id,
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue(),
                        cart.size,
                        cart.quantity
                ))
                .from(cart)
                .join(cart.merchandise, merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        cart.consumer.id.eq(consumerId)
                )
                .orderBy(cart.id.desc())
                .fetch();
    }
}
