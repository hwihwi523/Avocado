package com.avocado.product.repository;

import com.avocado.product.entity.Consumer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.avocado.product.entity.QConsumer.consumer;

@Repository
@RequiredArgsConstructor
public class ConsumerRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 사용자 ID로 데이터 조회
     * @param consumerId : 상품 ID
     * @return : 해당 ID를 갖는 사용자
     */
    public Consumer findById(UUID consumerId) {
        return queryFactory
                .selectFrom(consumer)
                .where(consumer.id.eq(consumerId))
                .fetchOne();
    }
}
