package com.avocado.community.db.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.UUID;

@Mapper
public interface StyleshotLikeRepository {

    void like(long styleshotId, UUID consumerId);

    void unlike(long styleshotId, UUID consumerId);
}
