package com.avocado.product.repository;

import com.avocado.product.dto.query.MaxTypeDTO;
import com.avocado.product.dto.query.QMaxTypeDTO;
import com.avocado.product.dto.query.QTotalScoreDTO;
import com.avocado.product.dto.query.TotalScoreDTO;
import com.avocado.product.entity.QAgeGenderScore;
import com.avocado.product.entity.QMbtiScore;
import com.avocado.product.entity.QPersonalColorScore;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.avocado.product.entity.QAgeGenderScore.ageGenderScore;
import static com.avocado.product.entity.QMbti.mbti;
import static com.avocado.product.entity.QMbtiScore.mbtiScore;
import static com.avocado.product.entity.QPersonalColor.personalColor;
import static com.avocado.product.entity.QPersonalColorScore.personalColorScore;

@Repository
@RequiredArgsConstructor
public class ScoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 상품들의 대표 퍼스널컬러를 한 번에 조회하는 쿼리
     * @param merchandiseIds : 상품 ID 목록
     * @return : 대표 퍼스널컬러
     */
    public List<MaxTypeDTO> findPersonalColors(List<Long> merchandiseIds) {
        if (merchandiseIds.isEmpty())
            return new ArrayList<>();

        // 최대 행동 점수를 갖는 퍼스널컬러를 찾기 위한 서브 쿼리
        QPersonalColorScore sub_pc_score = new QPersonalColorScore("sub_personal_color_score");
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(sub_pc_score.score.max())
                .where(sub_pc_score.merchandise.id.eq(personalColorScore.merchandise.id))
                .from(sub_pc_score)
                .groupBy(sub_pc_score.merchandise.id);

        return queryFactory
                .select(new QMaxTypeDTO(
                        personalColorScore.merchandise.id,
                        personalColor.kind
                ))
                .from(personalColorScore)
                .join(personalColorScore.personalColor, personalColor)
                .where(
                        personalColorScore.merchandise.id.in(merchandiseIds),
                        personalColorScore.score.eq(subQuery)  // 최대 행동점수를 갖는 퍼스널컬러 찾기
                )
                .orderBy(personalColorScore.merchandise.id.desc())
                .fetch();
    }
    // 단건 조회
    public String findPersonalColor(Long merchandiseId) {
        return queryFactory
                .select(
                        personalColor.kind
                )
                .from(personalColorScore)
                .join(personalColorScore.personalColor, personalColor)
                .where(
                        personalColorScore.merchandise.id.eq(merchandiseId)
                )
                .groupBy(
                        personalColorScore.merchandise.id,
                        personalColorScore.personalColor.id
                )
                .orderBy(
                        personalColorScore.score.sum().desc()
                )
                .fetchFirst();
    }

    /**
     * 특정 상품의 대표 MBTI를 조회하는 쿼리
     * @param merchandiseIds : 상품 ID 목록
     * @return : 대표 MBTI
     */
    public List<MaxTypeDTO> findMbtis(List<Long> merchandiseIds) {
        if (merchandiseIds.isEmpty())
            return new ArrayList<>();

        // 최대 행동 점수를 갖는 MBTI를 찾기 위한 서브 쿼리
        QMbtiScore sub_mbti_score = new QMbtiScore("sub_mbti_score");
        JPQLQuery<Long> subQuery = JPAExpressions
                .select(sub_mbti_score.score.max())
                .from(sub_mbti_score)
                .where(sub_mbti_score.merchandise.id.eq(mbtiScore.merchandise.id))
                .groupBy(sub_mbti_score.merchandise.id);

        return queryFactory
                .select(new QMaxTypeDTO(
                        mbtiScore.merchandise.id,
                        mbti.kind
                ))
                .from(mbtiScore)
                .join(mbtiScore.mbti, mbti)
                .where(
                        mbtiScore.merchandise.id.in(merchandiseIds),
                        mbtiScore.score.eq(subQuery)  // 최대 행동점수를 갖는 MBTI 찾기
                )
                .orderBy(mbtiScore.merchandise.id.desc())
                .fetch();
    }
    // 단건 조회
    public String findMbti(Long merchandiseId) {
        return queryFactory
                .select(mbti.kind)
                .from(mbtiScore)
                .join(mbtiScore.mbti, mbti)
                .where(
                        mbtiScore.merchandise.id.eq(merchandiseId)
                )
                .groupBy(
                        mbtiScore.merchandise.id,
                        mbtiScore.mbti.id
                )
                .orderBy(
                        mbtiScore.score.sum().desc()
                )
                .fetchFirst();
    }

    /**
     * 특정 상품의 대표 나이대를 조회하는 쿼리
     * @param merchandiseIds : 상품 ID 목록
     * @return : 대표 MBTI
     */
    public List<MaxTypeDTO> findAges(List<Long> merchandiseIds) {
        if (merchandiseIds.isEmpty())
            return new ArrayList<>();

        // 성별을 제외하고 나이대별 행동 점수 합을 구하는 쿼리
        QAgeGenderScore ags_for_total = new QAgeGenderScore("ags_for_total");
        List<TotalScoreDTO> totalData = queryFactory
                .select(new QTotalScoreDTO(
                            ags_for_total.merchandise.id,
                            ags_for_total.age.stringValue(),
                            ags_for_total.score.sum()
                        )
                )
                .from(ags_for_total)
                .where(ags_for_total.merchandise.id.in(merchandiseIds))
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
        List<MaxTypeDTO> result = new ArrayList<>();
        for (TotalScoreDTO row : totalData) {
            // 새로운 상품이 등장했다면 현재 상품 정보를 최댓값으로 설정
            // (order by로 sum 내림차순 정렬했기 때문에 처음 등장하는 값이 최댓값)
            if (curMerchandiseId == null || !curMerchandiseId.equals(row.getMerchandiseId())) {
                result.add(new MaxTypeDTO(row.getMerchandiseId(), row.getType()));
                // 직전 상품 ID 변경
                curMerchandiseId = row.getMerchandiseId();
                curMaxScore = row.getScore();
            }
            // 최댓값이 여러 개인 경우도 리스트에 저장
            else if (curMerchandiseId.equals(row.getMerchandiseId())
                    && curMaxScore.equals(row.getScore())) {
                result.add(new MaxTypeDTO(row.getMerchandiseId(), row.getType()));
            }
        }

        return result;
    }
    // 단건 조회
    public String findAge(Long merchandiseId) {
        return queryFactory
                .select(
                        ageGenderScore.age.stringValue()
                )
                .from(ageGenderScore)
                .where(
                        ageGenderScore.merchandise.id.eq(merchandiseId)
                )
                .groupBy(
                        ageGenderScore.merchandise.id,
                        ageGenderScore.age
                )
                .orderBy(
                        ageGenderScore.score.sum().desc()
                )
                .fetchFirst();
    }
}
