package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.common.codes.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.BitSet;

@Component
public class MerchandiseIdSetRepository {

    private final RBitSet bitSet;

    @Autowired
    MerchandiseIdSetRepository(RedissonClient redisson, RedisKeys redisKeys) {
        String key = redisKeys.merchandiseIdSet;
        this.bitSet = redisson.getBitSet(key);
    }

    public void setUse(Long merchandiseId) {
        bitSet.set(merchandiseId);
    }

    public BitSet getBitSet() {
        return bitSet.asBitSet();
    }

}
