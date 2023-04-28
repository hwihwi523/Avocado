package com.avocado.product.repository;

import com.avocado.product.dto.query.CartDTO;
import com.avocado.product.dto.query.QCartDTO;
import com.avocado.product.entity.Cart;
import com.avocado.product.entity.QMerchandise;
import com.avocado.product.entity.QMerchandiseGroup;
import com.avocado.product.entity.QStore;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

import static com.avocado.product.entity.QCart.cart;
import static com.avocado.product.entity.QConsumer.consumer;
import static com.avocado.product.entity.QMerchandise.merchandise;
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
        em.persist(cart);
    }
    public void delete(Cart cart) {
        em.remove(cart);
    }

    /**
     * 장바구니 내역 단건 조회
     * @param consumerId : 소비자 ID
     * @param merchandiseId : 상품 ID
     * @return : 장바구니 단건 내역
     */
    public Cart findByConsumerIdAndMerchandiseId(UUID consumerId, Long merchandiseId) {
        return queryFactory
                .selectFrom(cart)
                .where(
                        eqConsumerId(consumerId),
                        eqMerchandiseId(merchandiseId)
                )
                .fetchFirst();
    }

    /**
     * 특정 사용자의 장바구니 목록을 장바구니 ID 내림차순으로 정렬하여 조회
     * @param consumerId : 소비자 ID
     * @return : 조회 데이터
     */
    public List<CartDTO> findMyCart(UUID consumerId) {
        return queryFactory
                .select(new QCartDTO(
                        cart.id,
                        store.name,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice
                ))
                .from(cart)
                .join(cart.consumer, consumer).fetchJoin()
                .join(cart.merchandise, merchandise).fetchJoin()
                .join(merchandise.group, merchandiseGroup).fetchJoin()
                .join(merchandiseGroup.provider, store).fetchJoin()
                .where(
                        eqConsumerId(consumerId)
                )
                .orderBy(cart.id.desc())
                .fetch();
    }

    private BooleanExpression eqConsumerId(UUID consumerId) {
        return consumerId != null ? consumer.id.eq(consumerId) : null;
    }
    private BooleanExpression eqMerchandiseId(Long merchandiseId) {
        return merchandiseId != null ? merchandise.id.eq(merchandiseId) : null;
    }
}
