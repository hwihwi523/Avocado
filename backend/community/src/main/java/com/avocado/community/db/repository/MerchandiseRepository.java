package com.avocado.community.db.repository;

import com.avocado.community.api.response.MerchandiseResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MerchandiseRepository {

    List<MerchandiseResp> getWearById(long styleshotId);


}
