package com.avocado.product.repository;

import com.avocado.product.dto.query.QReviewDTO;
import com.avocado.product.dto.query.ReviewDTO;
import com.avocado.product.entity.*;
import com.avocado.product.exception.DataManipulationException;
import com.avocado.product.exception.ErrorCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

import static com.avocado.product.entity.QConsumer.consumer;
import static com.avocado.product.entity.QMbti.mbti;
import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QPersonalColor.personalColor;
import static com.avocado.product.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 생성 및 삭제
    public void save(Review review) {
        try {
            em.persist(review);
        } catch (PersistenceException e) {
            throw new DataManipulationException(ErrorCode.INVALID_INSERT);
        }
    }
    public void delete(Review review) {
        em.remove(review);
    }

    /**
     * 리뷰 + 상품 Fetch Join / 상품 정보 수정하기 위해 한 번에 조회
     * @param reviewId : 리뷰 ID
     * @return : 해당 ID를 가진 리뷰
     */
    public Review findByIdWithMerchandise(Long reviewId) {
        return queryFactory
                .selectFrom(review)
                .join(review.merchandise, merchandise).fetchJoin()
                .where(
                        eqReviewId(reviewId)
                )
                .fetchFirst();
    }

    /**
     * 특정 사용자가 특정 상품에 리뷰를 남겼는지 여부를 조회하는 쿼리
     * @param consumerId : 사용자 ID
     * @param merchandiseId : 상품 ID
     * @return : true / false
     */
    public Boolean checkReviewed(UUID consumerId, Long merchandiseId) {
        // 특정 사용자가 특정 상품에 남긴 리뷰 조회
        Long reviewId = queryFactory
                .select(review.id)
                .from(review)
                .where(
                        review.consumer.id.eq(consumerId),
                        review.merchandise.id.eq(merchandiseId)
                )
                .fetchFirst();
        // 리뷰 여부 반환
        return reviewId != null;
    }

    /**
     * 특정 상품에 등록된 모든 리뷰를 조회하는 쿼리
     * @param merchandiseId : 상품 ID
     * @return : 상품에 등록된 모든 리뷰 (등록일자 기준 오름차순 정렬)
     */
    public List<ReviewDTO> findByMerchandiseId(Long merchandiseId) {
        return queryFactory
                .select(new QReviewDTO(
                        review.id,
                        consumer.name,
                        consumer.pictureUrl,
                        mbti.kind,
                        personalColor.kind,
                        review.score,
                        review.content,
                        review.createdAt
                ))
                .from(review)
                .join(review.merchandise, merchandise)
                .leftJoin(review.consumer, consumer)
                .leftJoin(consumer.mbti, mbti)
                .leftJoin(consumer.personalColor, personalColor)
                .where(
                        eqMerchandiseId(merchandiseId)
                )
                .orderBy(
                        review.createdAt.asc()
                )
                .fetch();
    }

    public Boolean existsReview(UUID reviewerId, Long merchandiseId) {
        Long reviewId = queryFactory
                .select(review.id)
                .from(review)
                .where(
                        review.consumer.id.eq(reviewerId),
                        review.merchandise.id.eq(merchandiseId)
                )
                .fetchFirst();
        return reviewId != null;
    }

    // 상품 ID 조건
    private BooleanExpression eqMerchandiseId(Long merchandiseId) {
        return merchandiseId != null ? merchandise.id.eq(merchandiseId) : null;
    }
    // 리뷰 ID 조건
    private BooleanExpression eqReviewId(Long reviewId) {
        return reviewId != null ? review.id.eq(reviewId) : null;
    }
}
