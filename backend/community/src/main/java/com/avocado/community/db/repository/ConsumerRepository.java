package com.avocado.community.db.repository;

import com.avocado.community.db.entity.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ConsumerRepository {
    Optional<Consumer> findById(UUID id);
}
