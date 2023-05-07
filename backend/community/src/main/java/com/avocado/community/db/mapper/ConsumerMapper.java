package com.avocado.community.db.mapper;

import com.avocado.community.db.entity.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsumerMapper {

    List<Consumer> getConsumerList();
}
