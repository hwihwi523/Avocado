package com.avocado.statistics.db.mysql.repository.mybatis;

import com.avocado.statistics.db.mysql.repository.dto.MerchandiseGroupDTO;
import com.avocado.statistics.db.mysql.repository.dto.MerchandiseMainDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MerchandiseRepository {

    Optional<MerchandiseMainDTO> getInfoById(long merchandiseId);

    Optional<MerchandiseGroupDTO> getInfoByGroupId(long groupId);

    String getMBTITag(long merchandiseId);

    String getPersonalColorTag(long merchandiseId);

    Integer getAgeGroup(long merchandiseId);

    Optional<Integer> wishlistExists(UUID consumerId, long merchandiseId);

}
