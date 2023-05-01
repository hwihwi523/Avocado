package com.avocado.product.repository;

import com.avocado.product.config.OrderByNull;
import com.avocado.product.dto.query.QScoreDTO;
import com.avocado.product.dto.query.ScoreDTO;
import com.avocado.product.entity.QMbti;
import com.avocado.product.entity.QMbtiScore;
import com.avocado.product.entity.QMerchandise;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.avocado.product.entity.QMbti.mbti;
import static com.avocado.product.entity.QMbtiScore.mbtiScore;
import static com.avocado.product.entity.QMerchandise.merchandise;

@Repository
@RequiredArgsConstructor
public class MbtiScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 특정 상품의 대표 MBTI를 조회하는 쿼리
     * @param merchandiseIds : 상품 ID 목록
     * @return : 대표 MBTI
     */
    public List<ScoreDTO> findMbtis(List<Long> merchandiseIds) {
        return queryFactory
                .select(new QScoreDTO(
                        merchandise.id,
                        mbti.kind,
                        mbtiScore.score.sum()
                ))
                .from(mbtiScore)
                .join(mbtiScore.mbti, mbti)
                .join(mbtiScore.merchandise, merchandise)
                .where(
                        inMerchandiseIds(merchandiseIds)
                )
                .groupBy(
                        merchandise.id,
                        mbti.kind
                )
                .orderBy(
                        OrderByNull.DEFAULT
                )
                .fetch();
    }

    // 상품 ID 조건
    private BooleanExpression inMerchandiseIds(List<Long> merchandiseIds) {
        return merchandiseIds != null ? merchandise.id.in(merchandiseIds) : null;
    }
}
