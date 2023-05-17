package com.avocado.statistics.db.mysql.repository.jpa;

import com.avocado.statistics.db.mysql.repository.dto.CategoryDistributionDTO;
import com.avocado.statistics.db.mysql.repository.dto.QCategoryDistributionDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.avocado.statistics.db.mysql.entity.jpa.QConsumer.consumer;
import static com.avocado.statistics.db.mysql.entity.jpa.QMerchandise.merchandise;
import static com.avocado.statistics.db.mysql.entity.jpa.QMerchandiseGroup.merchandiseGroup;
import static com.avocado.statistics.db.mysql.entity.jpa.QPurchase.purchase;
import static com.avocado.statistics.db.mysql.entity.jpa.QPurchasedMerchandise.purchasedMerchandise;

@Repository
@RequiredArgsConstructor
public class ConsumerStatisticsRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 특정 사용자가 사용한 금액의 총액 조회
     * @param consumerId : 사용자 ID
     * @return : 해당 사용자가 사용한 총액
     */
    public Long getTotalMoneySpent(UUID consumerId) {
        return queryFactory
                .select(purchase.totalPrice.sum())
                .from(purchase)
                .where(purchase.consumer.id.eq(consumerId))
                .fetchOne();
    }

    /**
     * 특정 사용자가 구매한 상품들의 카테고리 분포 조회
     * @param consumerId : 사용자 ID
     * @return : 해당 사용자가 구매한 상품의 카테고리 분포
     */
    public List<CategoryDistributionDTO> getCategoryDistribution(UUID consumerId) {
        return queryFactory
                .select(new QCategoryDistributionDTO(
                        merchandiseGroup.category.id,
                        merchandiseGroup.category.id.count()
                ))
                .from(purchasedMerchandise)
                .join(purchasedMerchandise.purchase, purchase)
                .join(purchase.consumer, consumer)
                .join(purchasedMerchandise.merchandise, merchandise)
                .join(merchandise.group, merchandiseGroup)
                .where(consumer.id.eq(consumerId))
                .groupBy(merchandiseGroup.category.id)
                .fetch();
    }
}
