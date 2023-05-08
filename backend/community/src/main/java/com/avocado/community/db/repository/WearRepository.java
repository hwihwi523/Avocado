package com.avocado.community.db.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WearRepository {

    void save(long styleshotId, long merchandiseId);

    List<Long> getAllByStyleshotId(long styleshotId);

    void deleteAllByStyleshotId(long styleshotId);
}
