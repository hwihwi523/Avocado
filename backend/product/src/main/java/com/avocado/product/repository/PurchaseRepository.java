package com.avocado.product.repository;

import com.avocado.product.dto.query.PurchaseHistoryMerchandiseDTO;
import com.avocado.product.dto.query.QPurchaseHistoryMerchandiseDTO;
import com.avocado.product.entity.QPurchasedMerchandise;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QMerchandiseCategory.merchandiseCategory;
import static com.avocado.product.entity.QMerchandiseGroup.merchandiseGroup;
import static com.avocado.product.entity.QPurchase.purchase;
import static com.avocado.product.entity.QPurchasedMerchandise.purchasedMerchandise;
import static com.avocado.product.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class PurchaseRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 특정 사용자가 특정 상품을 구매했는지 여부를 조회하는 쿼리
     * @param consumerId : 사용자 ID
     * @param merchandiseId : 상품 ID
     * @return : true / false
     */
    public Boolean checkPurchased(UUID consumerId, Long merchandiseId) {
        // 특정 사용자가 특정 상품을 구매한 내역 조회
        UUID purchaseId = queryFactory
                .select(purchase.id)
                .from(purchasedMerchandise)
                .join(purchasedMerchandise.purchase, purchase)
                .where(
                        purchase.consumer.id.eq(consumerId),
                        purchasedMerchandise.merchandise.id.eq(merchandiseId)
                )
                .fetchFirst();
        // 내역 존재 여부 반환
        return purchaseId != null;
    }

    /**
     * 구매내역을 조회하는 쿼리
     * @param consumerId : 사용자 ID
     * @param lastPurchaseDate : 마지막으로 조회한 상품의 구매일자
     * @param size : 페이징 정보, null이면 최대로 조회
     * @return : 구매내역 리스트
     */
    public Page<PurchaseHistoryMerchandiseDTO> findPurchaseHistories(UUID consumerId, LocalDateTime lastPurchaseDate, Integer size) {
        // 카운트 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(purchase.id.count())
                .from(purchase)
                .where(
                        purchase.consumer.id.eq(consumerId),
                        ltCreatedAt(lastPurchaseDate)
                );

        // size 설정이 null 이면 모든 행을 가져오도록 page size 변경
        Pageable pageable = PageRequest.ofSize(
                size != null && size > 0
                        ? size
                        : Integer.MAX_VALUE
        );

        // 구매내역 조회
        List<PurchaseHistoryMerchandiseDTO> content = queryFactory
                .select(new QPurchaseHistoryMerchandiseDTO(
                        purchase.id,
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        purchase.createdAt,
                        purchasedMerchandise.size
                ))
                .from(purchasedMerchandise)
                .join(purchasedMerchandise.provider, store)
                .join(purchasedMerchandise.purchase, purchase)
                .join(purchasedMerchandise.merchandise, merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        purchase.consumer.id.eq(consumerId),
                        ltCreatedAt(lastPurchaseDate)
                )
                .orderBy(
                        purchase.createdAt.desc()
                )
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
    private BooleanExpression ltCreatedAt(LocalDateTime lastPurchaseDate) {
        return lastPurchaseDate != null ? purchase.createdAt.lt(lastPurchaseDate) : null;
    }
}
