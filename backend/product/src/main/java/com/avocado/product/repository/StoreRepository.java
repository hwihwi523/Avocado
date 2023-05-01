package com.avocado.product.repository;

import com.avocado.product.entity.QStore;
import com.avocado.product.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.avocado.product.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class StoreRepository {
    private final JPAQueryFactory queryFactory;

    public Store findById(UUID providerId) {
        return queryFactory
                .selectFrom(store)
                .where(store.providerId.eq(providerId))
                .fetchOne();
    }
}
