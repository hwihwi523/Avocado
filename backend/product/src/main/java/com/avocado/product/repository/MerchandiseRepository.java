package com.avocado.product.repository;

import com.avocado.product.entity.Merchandise;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.avocado.product.entity.QMerchandise.merchandise;

@Repository
@RequiredArgsConstructor
public class MerchandiseRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 상품 ID로 데이터 조회
     * @param merchandiseId : 상품 ID
     * @return : 해당 ID를 갖는 상품
     */
    public Merchandise findById(Long merchandiseId) {
        return queryFactory
                .selectFrom(merchandise)
                .where(merchandise.id.eq(merchandiseId))
                .fetchOne();
    }
}
