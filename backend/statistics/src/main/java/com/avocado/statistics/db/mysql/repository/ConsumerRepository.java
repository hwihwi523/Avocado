package com.avocado.statistics.db.mysql.repository;

import com.avocado.statistics.db.mysql.entity.mybatis.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ConsumerRepository {

    Optional<Consumer> getById(UUID consumerId);



}
