package com.avocado.community.db.repository;

import com.avocado.community.api.response.StyleshotResp;
import com.avocado.community.db.entity.Styleshot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.swing.text.Style;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StyleshotRepository {

    List<Styleshot> getAll();

    List<Styleshot> getAllPageable(Long lastId, Integer resultSize);

    List<Styleshot> getAllFirstPageable(Integer resultSize);

    List<Styleshot> getAllByConsumerId(@Param("consumerId") UUID consumerId);

    int getStyleshotCnt(@Param("consumerId") UUID consumerId);

    int getLikeCnt(@Param("consumerId") UUID consumerId);

    Optional<Styleshot> getById(long styleshotId);

    void save(Styleshot styleshot);

    void deleteById(long styleshotId);

    Optional<Long> getByIdAndConsumerId(long styleshotId, @Param("consumerId") UUID consumerId);

}
