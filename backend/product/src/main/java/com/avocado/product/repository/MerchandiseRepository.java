package com.avocado.product.repository;

import com.avocado.product.dto.query.*;
import com.avocado.product.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static com.avocado.product.entity.QClick.click;
import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QMerchandiseAdditionalImg.merchandiseAdditionalImg;
import static com.avocado.product.entity.QMerchandiseCategory.merchandiseCategory;
import static com.avocado.product.entity.QMerchandiseGroup.merchandiseGroup;
import static com.avocado.product.entity.QPurchasedMerchandise.purchasedMerchandise;
import static com.avocado.product.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class MerchandiseRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    /**
     * 프록시 객체 조회
     * @param merchandiseId : 상품 ID
     * @return : 상품 ID를 갖는 프록시 객체
     */
    public Merchandise getOne(Long merchandiseId) {
        return em.getReference(Merchandise.class, merchandiseId);
    }

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

    /**
     * 사용자가 최근 본 상품 목록을 조회하는 쿼리 (최대 5개)
     * @param consumerId : 사용자 ID
     * @return : 해당 사용자의 최근 조회 상품 목록
     */
    public List<ClickMerchandiseDTO> findRecentMerchandises(UUID consumerId) {
        // 사용자가 최근 조회한 5개의 상품 ID 구하기
        List<Long> merchandiseIds = queryFactory
                .select(click.merchandise.id).distinct()
                .from(click)
                .where(click.consumer.id.eq(consumerId))
                .limit(5)
                .fetch();

        // 최근 조회한 상품이 없다면 빈 리스트 반환
        if (merchandiseIds.isEmpty())
            return new ArrayList<>();

        // 해당 상품 내역 조회
        return queryFactory
                .select(new QClickMerchandiseDTO(
                        click.id.max(),
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue()
                ))
                .from(click)
                .join(click.merchandise, merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        merchandise.id.in(merchandiseIds)
                )
                .groupBy(
                        merchandise.id
                )
                .orderBy(
                        click.id.max().desc()
                )
                .fetch();
    }

    /**
     * 카테고리와 브랜드 이름을 기준으로 상품을 조회하고 상품 ID 내림차순 정렬
     * (페이징 + No offset 기법 적용)
     * @param categoryId : 카테고리 ID
     * @param providerId : 스토어 ID
     * @param lastMerchandiseId : 마지막으로 조회한 상품 ID
     * @param pageable : 페이지 크기
     * @return : 조회 데이터, 마지막 페이지 여부, 마지막 상품 ID
     */
    public Page<SimpleMerchandiseDTO> findByCategoryAndBrand_NoOffset(Short categoryId, UUID providerId,
                                                                    Long lastMerchandiseId, Pageable pageable) {
        // 데이터 조회
        List<SimpleMerchandiseDTO> result = queryFactory
                .select(new QSimpleMerchandiseDTO(
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue()
                ))
                .from(merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        ltLastMerchandiseId(lastMerchandiseId),
                        eqCategoryId(categoryId),
                        eqProviderId(providerId)
                )
                .orderBy(
                        merchandise.id.desc()
                )
                .limit(pageable.getPageSize())
                .fetch();

        // Count 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(merchandise.id.count())
                .from(merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .where(
                        eqCategoryId(categoryId),
                        eqProviderId(providerId)
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    /**
     * 특정 상품의 상세정보를 조회하는 쿼리
     * @param merchandiseId : 조회하려는 상품 ID
     * @return : 해당 상품의 상세정보
     */
    public DetailMerchandiseDTO findDetailMerchandise(Long merchandiseId) {
        return queryFactory
                .select(new QDetailMerchandiseDTO(
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        store.providerId,
                        merchandise.inventory,
                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue(),
                        merchandiseGroup.description
                ))
                .from(merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        eqMerchandiseId(merchandiseId)
                )
                .fetchFirst();
    }

    /**
     * 특정 상품의 추가 이미지 리스트를 조회하는 쿼리
     * @param merchandiseId : 조회하려는 상품의 ID
     * @return : 해당 상품의 추가 이미지 리스트
     */
    public List<String> findAdditionalImages(Long merchandiseId) {
        return queryFactory
                .select(
                        merchandiseAdditionalImg.imgurl
                )
                .from(merchandiseAdditionalImg)
                .join(merchandiseAdditionalImg.merchandise, merchandise)
                .where(
                        eqMerchandiseId(merchandiseId)
                )
                .fetch();
    }

    /**
     * 가장 많이 팔린 상품 상위 N개를 조회하는 쿼리
     * @param lastMerchandiseId : for pagination
     * @param pageable : for pagination
     * @return : 가장 많이 팔린 상품 상위 N개
     */
    public Page<SimpleMerchandiseDTO> findPopularMerchandises_NoOffset(Long lastMerchandiseId, Pageable pageable) {
        // 가장 많이 팔린 상품 ID N개 조회
        List<Long> popularIds = queryFactory
                .select(purchasedMerchandise.merchandise.id)
                .from(purchasedMerchandise)
                .groupBy(purchasedMerchandise.merchandise.id)
                .orderBy(purchasedMerchandise.merchandise.id.count().desc())
                .limit(pageable.getPageSize())
                .fetch();

        // 데이터 조회
        List<SimpleMerchandiseDTO> result = queryFactory
                .select(new QSimpleMerchandiseDTO(
                        store.name,
                        merchandise.id,
                        merchandiseCategory.nameKor,
                        merchandise.imgurl,
                        merchandise.name,
                        merchandiseGroup.price,
                        merchandiseGroup.discountedPrice,
                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue()
                ))
                .from(merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store)
                .join(merchandiseGroup.category, merchandiseCategory)
                .where(
                        ltLastMerchandiseId(lastMerchandiseId),
                        merchandise.id.in(popularIds)
                )
                .orderBy(merchandise.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        // 인기순으로 순서 재구성
        Map<Long, Integer> indexOf = new HashMap<>();
        int index = 0;
        for (SimpleMerchandiseDTO data : result)
            indexOf.put(data.getMerchandiseId(), index++);

        List<SimpleMerchandiseDTO> sortedResult = new ArrayList<>();
        for (Long popularId : popularIds)
            sortedResult.add(result.get(indexOf.get(popularId)));

        // Count 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(merchandise.id.count())
                .from(merchandise)
                .join(merchandise.group, merchandiseGroup)
                .join(merchandiseGroup.provider, store);

        return PageableExecutionUtils.getPage(sortedResult, pageable, countQuery::fetchOne);
    }

    // 상품 ID 조건
    private BooleanExpression eqMerchandiseId(Long merchandiseId) {
        return merchandiseId != null ? merchandise.id.eq(merchandiseId) : null;
    }

    // 카테고리 ID 조건
    private BooleanExpression eqCategoryId(Short categoryId) {
        return categoryId != null ? merchandiseGroup.category.id.eq(categoryId) : null;
    }

    // 브랜드 이름 조건
    private BooleanExpression eqProviderId(UUID providerId) {
        if (providerId == null)
            return null;
        return store.providerId.eq(providerId);
    }

    // 마지막 조회한 상품 ID 조건
    private BooleanExpression ltLastMerchandiseId(Long lastMerchandiseId) {
        return lastMerchandiseId != null ? merchandise.id.lt(lastMerchandiseId) : null;
    }

    // Offset 사용 버전.
//    public Page<SimpleMerchandiseDTO> findByCategoryAndBrand_Offset(Short categoryId, String brandName,
//                                                                    Pageable pageable) {
//        // 데이터 조회
//        List<SimpleMerchandiseDTO> result = queryFactory
//                .select(new QSimpleMerchandiseDTO(
//                        merchandise.id,
//                        store.name,
//                        merchandise.id,
//                        merchandiseCategory.nameKor,
//                        merchandise.name,
//                        merchandiseGroup.price,
//                        merchandiseGroup.discountedPrice,
//                        merchandise.totalScore.divide(merchandise.reviewCount).floatValue()
//                ))
//                .from(merchandise)
//                .join(merchandise.group, merchandiseGroup)
//                .join(merchandiseGroup.provider, store)
//                .join(merchandiseGroup.category, merchandiseCategory)
//                .where(
//                        eqCategoryId(categoryId),
//                        eqBrandName(brandName)
//                )
//                .orderBy(
//                        merchandise.id.desc()
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        // Count 쿼리
//        JPAQuery<Long> countQuery = queryFactory
//                .select(merchandise.id.count())
//                .from(merchandise)
//                .join(merchandise.group, merchandiseGroup)
//                .join(merchandiseGroup.provider, store)
//                .join(merchandiseGroup.category, merchandiseCategory)
//                .where(
//                        eqCategoryId(categoryId),
//                        eqBrandName(brandName)
//                );
//
//        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
//    }
}
