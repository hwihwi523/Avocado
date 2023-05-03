package com.avocado.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.avocado.product.entity.QPurchase.purchase;

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
                .from(purchase)
                .where(
                        purchase.consumer.id.eq(consumerId),
                        purchase.merchandise.id.eq(merchandiseId)
                )
                .fetchFirst();
        // 내역 존재 여부 반환
        return purchaseId != null;
    }
}
