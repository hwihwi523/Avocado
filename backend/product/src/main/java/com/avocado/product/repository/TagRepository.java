package com.avocado.product.repository;

import com.avocado.product.dto.query.QSimpleMerchandiseDTO;
import com.avocado.product.dto.query.SimpleMerchandiseDTO;
import com.avocado.product.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.avocado.product.entity.QMerchandise.merchandise;
import static com.avocado.product.entity.QMerchandiseGroup.merchandiseGroup;
import static com.avocado.product.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    private final JPAQueryFactory queryFactory;
    private final MerchandiseRepository merchandiseRepository;

    public Tag findById(Long merchandiseId) {
        return queryFactory
                .selectFrom(tag)
                .where(tag.merchandise.id.eq(merchandiseId))
                .fetchOne();
    }
    public List<Tag> findAll(Collection<Long> merchandiseIds) {
        return queryFactory
                .selectFrom(tag)
                .where(tag.merchandise.id.in(merchandiseIds))
                .fetch();
    }

    public List<SimpleMerchandiseDTO> getRelatedMerchandises(Long merchandiseId, Integer size) {
        Tag keyTag = queryFactory
                .selectFrom(tag)
                .join(tag.merchandise, merchandise).fetchJoin()
                .join(merchandise.group, merchandiseGroup).fetchJoin()
                .where(merchandise.id.eq(merchandiseId))
                .fetchOne();

        List<Long> merchandiseIds = queryFactory
                .select(merchandise.id)
                .from(tag)
                .join(tag.merchandise, merchandise)
                .join(merchandise.group, merchandiseGroup)
                .where(
                        merchandiseGroup.category.id.eq(keyTag.getMerchandise().getGroup().getCategory().getId()),
                        checkRelated(
                                keyTag.getMbti(),
                                keyTag.getPersonalColor(),
                                keyTag.getAgeGroup()
                        )
                )
                .limit(size)
                .fetch();

        return merchandiseRepository.findMerchandises(merchandiseIds);
    }

    private BooleanBuilder checkRelated(Mbti mbti, PersonalColor personalColor, Short ageGroup) {
        BooleanBuilder builder = new BooleanBuilder();

        if (mbti != null)
            builder.or(tag.mbti.id.eq(mbti.getId()));
        if (personalColor != null)
            builder.or(tag.personalColor.id.eq(personalColor.getId()));
        if (ageGroup != null)
            builder.or(tag.ageGroup.eq(ageGroup));

        return builder;
    }
    private BooleanExpression eqMbtiId(Byte mbtiId) {
        return mbtiId != null ? tag.mbti.id.eq(mbtiId) : null;
    }
    private BooleanExpression eqPersonalColorId(Byte personalColorId) {
        return personalColorId != null ? tag.personalColor.id.eq(personalColorId) : null;
    }
    private BooleanExpression eqAgeGroup(Short ageGroup) {
        return ageGroup != null ? tag.ageGroup.eq(ageGroup) : null;
    }
}
