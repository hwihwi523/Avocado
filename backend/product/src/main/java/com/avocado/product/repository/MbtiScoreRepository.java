package com.avocado.product.repository;

import com.avocado.product.entity.QMbti;
import com.avocado.product.entity.QMbtiScore;
import com.avocado.product.entity.QMerchandise;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.avocado.product.entity.QMbti.mbti;
import static com.avocado.product.entity.QMbtiScore.mbtiScore;
import static com.avocado.product.entity.QMerchandise.merchandise;

@Repository
@RequiredArgsConstructor
public class MbtiScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 특정 상품의 대표 MBTI를 조회하는 쿼리
     * @param merchandiseId : 상품 ID
     * @return : 대표 MBTI
     */
    public String findMbti(Long merchandiseId) {
        return queryFactory
                .select(mbti.kind)
                .from(mbtiScore)
                .join(mbtiScore.mbti, mbti)
                .where(
                        eqMerchandiseId(merchandiseId)
                )
                .groupBy(mbtiScore.merchandise,
                        mbtiScore.mbti)
                .orderBy(mbtiScore.score.desc())
                .fetchFirst();
    }

    // 상품 ID 조건
    private BooleanExpression eqMerchandiseId(Long merchandiseId) {
        return merchandiseId != null ? merchandise.id.eq(merchandiseId) : null;
    }
}
