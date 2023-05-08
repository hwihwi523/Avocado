package com.avocado.community.db.repository;

import com.avocado.community.api.response.StyleshotResp;
import com.avocado.community.db.entity.Styleshot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StyleshotRepository {

    List<StyleshotResp> getAll();

    List<StyleshotResp> getAllByConsumerId(UUID consumerId);

    Optional<StyleshotResp> getById(long styleshotId);

    void save(Styleshot styleshot);

    void deleteById(long styleshotId);

    Optional<Long> getByIdAndConsumerId(long styleshotId, UUID consumerId);

}
