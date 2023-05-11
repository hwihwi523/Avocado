package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.common.codes.RedisKeys;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.kafka.dto.Type;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdvertiseCountRepository {

    private final RedisKeys redisKeys;
    private final RedissonClient redisson;

    public void save(Type resType, Long merchandiseId) {
        RMap<Long, Long> map = redisson.getMap(getKey(resType));
        map.putIfAbsent(merchandiseId, 0L);
        Long cnt = map.get(merchandiseId);
        map.put(merchandiseId, cnt + 1);
    }

    public Map<Long, Long> getMap(Type resType) {
        RMap<Long, Long> map = redisson.getMap(getKey(resType));
        return map.readAllMap();
    }

    public boolean deleteMap(Type resType) {
        RMap<Long, Long> map = redisson.getMap(getKey(resType));
        return map.delete();
    }

    private String getKey(Type resType) {
        StringBuilder sb =  new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(redisKeys.adPrefix);
        switch (resType) {
            case AD_VIEW:
                sb.append(redisKeys.viewPrefix);
                break;
            case AD_CLICK:
                sb.append(redisKeys.clickPrefix);
                break;
            case AD_PAYMENT:
                sb.append(redisKeys.paymentPrefix);
                break;
            default:
                throw new BaseException(ResponseCode.INVALID_VALUE);
        }
        return sb.toString();
    }


}
