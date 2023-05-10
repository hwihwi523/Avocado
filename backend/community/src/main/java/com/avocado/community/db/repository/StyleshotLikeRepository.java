package com.avocado.community.db.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface StyleshotLikeRepository {

    void like(long styleshotId, @Param("consumerId") UUID consumerId);

    void unlike(long styleshotId, @Param("consumerId") UUID consumerId);
}
