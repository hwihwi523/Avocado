package com.avocado.community.db.repository;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface WearRepository {

    void save(long styleshotId, long merchandiseId);

    void deleteAllByStyleshotId(long styleshotId);
}
