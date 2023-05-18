package com.avocado.product.repository;

import com.avocado.product.dto.query.*;
import com.avocado.product.entity.QAgeGenderScore;
import com.avocado.product.entity.QMbtiScore;
import com.avocado.product.entity.QPersonalColorScore;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.avocado.product.entity.QMbtiScore.mbtiScore;
import static com.avocado.product.entity.QPersonalColorScore.personalColorScore;

@Repository
@RequiredArgsConstructor
public class ScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 행동점수를 갖는 모든 상품들의 대표 퍼스널컬러를 한 번에 조회하는 쿼리
     * @return : 모든 상품들의 대표 퍼스널컬러
     */
    public List<MaxPersonalColorDTO> findPersonalColors(List<Long> merchandiseIds) {
        // 최대 행동 점수를 갖는 퍼스널컬러를 찾기 위한 서브 쿼리
        QPersonalColorScore sub_pc_score = new QPersonalColorScore("sub_personal_color_score");
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(sub_pc_score.score.max())
                .where(sub_pc_score.merchandise.id.eq(personalColorScore.merchandise.id))
                .from(sub_pc_score)
                .groupBy(sub_pc_score.merchandise.id);

        return queryFactory
                .select(new QMaxPersonalColorDTO(
                        personalColorScore.merchandise.id,
                        personalColorScore.personalColor.id
                ))
                .from(personalColorScore)
                .where(
                        inPersonalColorMerchandiseId(merchandiseIds),
                        personalColorScore.score.eq(subQuery)  // 최대 행동점수를 갖는 퍼스널컬러 찾기
                )
                .orderBy(personalColorScore.merchandise.id.desc())
                .fetch();
    }
    private BooleanExpression inPersonalColorMerchandiseId(List<Long> merchandiseIds) {
        return merchandiseIds != null ? personalColorScore.merchandise.id.in(merchandiseIds) : null;
    }

    /**
     * 행동점수를 갖는 모든 상품의 대표 MBTI를 조회하는 쿼리
     * @return : 모든 상품의 대표 MBTI
     */
    public List<MaxMbtiDTO> findMbtis(List<Long> merchandiseIds) {
        // 최대 행동 점수를 갖는 MBTI를 찾기 위한 서브 쿼리
        QMbtiScore sub_mbti_score = new QMbtiScore("sub_mbti_score");
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(sub_mbti_score.score.max())
                .from(sub_mbti_score)
                .where(sub_mbti_score.merchandise.id.eq(mbtiScore.merchandise.id))
                .groupBy(sub_mbti_score.merchandise.id);

        return queryFactory
                .select(new QMaxMbtiDTO(
                        mbtiScore.merchandise.id,
                        mbtiScore.mbti.id
                ))
                .from(mbtiScore)
                .where(
                        inMbtiMerchandiseId(merchandiseIds),
                        mbtiScore.score.eq(subQuery)  // 최대 행동점수를 갖는 MBTI 찾기
                )
                .orderBy(mbtiScore.merchandise.id.desc())
                .fetch();
    }
    private BooleanExpression inMbtiMerchandiseId(List<Long> merchandiseIds) {
        return merchandiseIds != null ? mbtiScore.merchandise.id.in(merchandiseIds) : null;
    }

    /**
     * 행동점수를 갖는 모든 특정 상품의 대표 나이대를 조회하는 쿼리
     * @return : 모든 상품들의 대표 MBTI
     */
    public List<MaxAgeGroupDTO> findAges(List<Long> merchandiseIds) {
        // 성별을 제외하고 나이대별 행동 점수 합을 구하는 쿼리
        QAgeGenderScore ags_for_total = new QAgeGenderScore("ags_for_total");
        List<TotalScoreDTO> totalData = queryFactory
                .select(new QTotalScoreDTO(
                            ags_for_total.merchandise.id,
                            ags_for_total.age,
                            ags_for_total.score.sum()
                        )
                )
                .from(ags_for_total)
                .where(
                        inAgsMerchandiseId(ags_for_total, merchandiseIds)
                )
                .groupBy(
                        ags_for_total.merchandise.id,
                        ags_for_total.age
                )
                .orderBy(
                        ags_for_total.merchandise.id.asc(),
                        ags_for_total.score.sum().desc()
                )
                .fetch();

        // 각 그룹의 최댓값 직접 찾기
        Long curMerchandiseId = null;  // 직전에 처리한 상품의 ID
        Long curMaxScore = null;  // 직전에 처리한 상품의 점수
        List<MaxAgeGroupDTO> result = new ArrayList<>();
        for (TotalScoreDTO row : totalData) {
            // 새로운 상품이 등장했다면 현재 상품 정보를 최댓값으로 설정
            // (order by로 sum 내림차순 정렬했기 때문에 처음 등장하는 값이 최댓값)
            if (curMerchandiseId == null || !curMerchandiseId.equals(row.getMerchandiseId())) {
                result.add(new MaxAgeGroupDTO(row.getMerchandiseId(), row.getAgeGroup()));
                // 직전 상품 ID 변경
                curMerchandiseId = row.getMerchandiseId();
                curMaxScore = row.getScore();
            }
            // 최댓값이 여러 개인 경우도 리스트에 저장
            else if (curMerchandiseId.equals(row.getMerchandiseId())
                    && curMaxScore.equals(row.getScore())) {
                result.add(new MaxAgeGroupDTO(row.getMerchandiseId(), row.getAgeGroup()));
            }
        }

        return result;
    }
    private BooleanExpression inAgsMerchandiseId(QAgeGenderScore ags_for_total, List<Long> merchandiseIds) {
        return merchandiseIds != null ? ags_for_total.merchandise.id.in(merchandiseIds) : null;
    }
}
