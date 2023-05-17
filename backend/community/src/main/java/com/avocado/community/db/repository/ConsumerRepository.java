package com.avocado.community.db.repository;

import com.avocado.SignupInfo;
import com.avocado.UpdateInfo;
import com.avocado.community.db.entity.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ConsumerRepository {
    Optional<Consumer> findById(UUID id);

    void save(UUID id, SignupInfo info);

    void updateInfo(UUID id, UpdateInfo info);

    void delete(UUID id);
}
