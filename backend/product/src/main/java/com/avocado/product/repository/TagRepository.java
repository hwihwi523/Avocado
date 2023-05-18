package com.avocado.product.repository;

import com.avocado.product.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.avocado.product.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    private final JPAQueryFactory queryFactory;

    public List<Tag> findAll(Set<Long> merchandiseIds) {
        return queryFactory
                .selectFrom(tag)
                .where(tag.merchandise.id.in(merchandiseIds))
                .fetch();
    }
}
