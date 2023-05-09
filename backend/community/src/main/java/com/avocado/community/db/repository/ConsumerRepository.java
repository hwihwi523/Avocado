package com.avocado.community.db.repository;

import com.avocado.community.db.entity.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsumerRepository {

    List<Consumer> getConsumerList();
}
