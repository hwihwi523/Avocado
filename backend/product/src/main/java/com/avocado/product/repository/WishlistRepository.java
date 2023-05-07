package com.avocado.product.repository;

import com.avocado.product.dto.query.QWishlistMerchandiseDTO;
import com.avocado.product.dto.query.WishlistMerchandiseDTO;
import com.avocado.product.entity.Wishlist;
import com.avocado.product.exception.DataManipulationException;
import com.avocado.product.exception.ErrorCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

import static com.avocado.product.entity.QConsumer.consumer;
import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QMerchandiseCategory.merchandiseCategory;
import static com.avocado.product.entity.QMerchandiseGroup.merchandiseGroup;
import static com.avocado.product.entity.QStore.store;
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
        try {
            em.persist(wishlist);
        } catch (PersistenceException e) {
            throw new DataManipulationException(ErrorCode.INVALID_INSERT);
        }
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

    /**
     * 특정 사용자의 찜 목록을 찜 ID 내림차순으로 정렬하여 조회
     * @param consumerId : 소비자 ID
     * @return : 조회 데이터
     */
    public List<WishlistMerchandiseDTO> findMyWishlist(UUID consumerId) {
        return queryFactory
                .select(new QWishlistMerchandiseDTO(
                        wishlist.id,
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue().as("score")
                ))
                .from(wishlist)
                .join(wishlist.consumer, consumer)
                .join(wishlist.merchandise, merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        eqConsumerId(consumerId)
                )
                .orderBy(wishlist.id.desc())
                .fetch();
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
