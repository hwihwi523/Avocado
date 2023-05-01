package com.avocado.product.repository;

import com.avocado.product.entity.Wishlist;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

import static com.avocado.product.entity.QConsumer.consumer;
import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QWishlist.wishlist;

@Repository
@RequiredArgsConstructor
public class WishlistRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    /**
     * 데이터 삽입 및 삭제
     */
    public void save(Wishlist wishlist) {
        em.persist(wishlist);
    }
    public void delete(Wishlist wishlist) {
        em.remove(wishlist);
    }

    /**
     * 단건의 찜 내역을 조회하는 쿼리
     * @param merchandiseId : 찜할 상품의 ID
     * @param consumerId : 찜한 회원의 ID
     * @return : 검색결과 (단건 or NULL)
     */
    public Wishlist searchWishlist(Long wishlistId, UUID consumerId, Long merchandiseId) {
        return queryFactory
                .selectFrom(wishlist)
                .where(
                        eqWishlistId(wishlistId),  // 찜 ID 조건
                        eqMerchandiseId(merchandiseId),  // 상품 ID 조건
                        eqConsumerId(consumerId)  // 구매자 ID 조건
                )
                .fetchFirst();
    }

    // 찜 ID 일치 여부
    private BooleanExpression eqWishlistId(Long wishlistId) {
        return wishlistId != null ? wishlist.id.eq(wishlistId) : null;
    }

    // 상품 ID 일치 여부
    private BooleanExpression eqMerchandiseId(Long merchandiseId) {
        return merchandiseId != null ? merchandise.id.eq(merchandiseId) : null;
    }

    // 구매자 ID 일치 여부
    private BooleanExpression eqConsumerId(UUID consumerId) {
        return consumerId != null ? consumer.id.eq(consumerId) : null;
    }
}
