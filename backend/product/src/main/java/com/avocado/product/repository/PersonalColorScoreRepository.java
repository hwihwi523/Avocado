package com.avocado.product.repository;

import com.avocado.product.entity.QMerchandise;
import com.avocado.product.entity.QPersonalColor;
import com.avocado.product.entity.QPersonalColorScore;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QPersonalColor.personalColor;
import static com.avocado.product.entity.QPersonalColorScore.personalColorScore;

@Repository
@RequiredArgsConstructor
public class PersonalColorScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 특정 상품의 대표 퍼스널컬러를 조회하는 쿼리
     * @param merchandiseId : 상품 ID
     * @return : 대표 퍼스널컬러
     */
    public String findPersonalColor(Long merchandiseId) {
        return queryFactory
                .select(personalColor.kind)
                .from(personalColorScore)
                .join(personalColorScore.personalColor, personalColor)
                .where(
                        eqMerchandiseId(merchandiseId)
                )
                .groupBy(personalColorScore.merchandise,
                        personalColorScore.personalColor)
                .orderBy(personalColorScore.score.desc())
                .fetchFirst();
    }

    // 상품 ID 조건
    private BooleanExpression eqMerchandiseId(Long merchandiseId) {
        return merchandiseId != null ? merchandise.id.eq(merchandiseId) : null;
    }
}
