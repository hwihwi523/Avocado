package com.avocado.product.repository;

import com.avocado.product.config.OrderByNull;
import com.avocado.product.dto.query.QScoreDTO;
import com.avocado.product.dto.query.ScoreDTO;
import com.avocado.product.entity.QAgeGenderScore;
import com.avocado.product.entity.QMerchandise;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.avocado.product.entity.QAgeGenderScore.ageGenderScore;
import static com.avocado.product.entity.QMerchandise.merchandise;

@Repository
@RequiredArgsConstructor
public class AgeGenderScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 특정 상품의 대표 나이대를 조회하는 쿼리
     * @param merchandiseIds : 상품 ID 목록
     * @return : 대표 MBTI
     */
    public List<ScoreDTO> findAges(List<Long> merchandiseIds) {
        return queryFactory
                .select(new QScoreDTO(
                        ageGenderScore.merchandise.id,
                        ageGenderScore.age.stringValue(),
                        ageGenderScore.score.sum()
                ))
                .from(ageGenderScore)
                .where(
                        inMerchandiseIds(merchandiseIds)
                )
                .groupBy(
                        ageGenderScore.merchandise.id,
                        ageGenderScore.age
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
