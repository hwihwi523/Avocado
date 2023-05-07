package com.avocado.product.repository;

import com.avocado.product.entity.Consumer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static com.avocado.product.entity.QConsumer.consumer;

@Repository
@RequiredArgsConstructor
public class ConsumerRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    /**
     * 프록시 객체 생성
     * @param consumerId : 사용자 ID
     * @return : 사용자 ID를 갖는 프록시 객체
     */
    public Consumer getOne(UUID consumerId) {
        return em.getReference(Consumer.class, consumerId);
    }

    /**
     * 사용자 ID로 데이터 조회
     * @param consumerId : 사용자 ID
     * @return : 해당 ID를 갖는 사용자
     */
    public Consumer findById(UUID consumerId) {
        return queryFactory
                .selectFrom(consumer)
                .where(consumer.id.eq(consumerId))
                .fetchOne();
    }
}
