package com.avocado.product.repository;

import com.avocado.product.config.OrderByNull;
import com.avocado.product.dto.query.QScoreDTO;
import com.avocado.product.dto.query.ScoreDTO;
import com.avocado.product.entity.QMerchandise;
import com.avocado.product.entity.QPersonalColor;
import com.avocado.product.entity.QPersonalColorScore;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QPersonalColor.personalColor;
import static com.avocado.product.entity.QPersonalColorScore.personalColorScore;

@Repository
@RequiredArgsConstructor
public class PersonalColorScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 상품들의 대표 퍼스널컬러를 한 번에 조회하는 쿼리
     * @param merchandiseIds : 상품 ID 목록
     * @return : 대표 퍼스널컬러
     */
    public List<ScoreDTO> findPersonalColors(List<Long> merchandiseIds) {
        return queryFactory
                .select(new QScoreDTO(
                        merchandise.id,
                        personalColor.kind,
                        personalColorScore.score.sum()
                ))
                .from(personalColorScore)
                .join(personalColorScore.personalColor, personalColor)
                .join(personalColorScore.merchandise, merchandise)
                .where(
                        inMerchandiseIds(merchandiseIds)
                )
                .groupBy(
                        merchandise.id,
                        personalColor.kind
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
