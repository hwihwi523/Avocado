package com.avocado.statistics.db.redis.repository;

import com.avocado.ActionType;
import com.avocado.statistics.common.codes.RedisKeys;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdvertiseCountRepository {

    private final RedisKeys redisKeys;
    private final RedissonClient redisson;

    public void save(ActionType resType, String date, Long merchandiseId) {
        RMap<Long, Integer> map = redisson.getMap(getKey(resType, date));
        map.putIfAbsent(merchandiseId, 0);
        Integer cnt = map.get(merchandiseId);
        map.put(merchandiseId, cnt + 1);
    }

    public Map<Long, Integer> getMap(ActionType resType, String date) {
        RMap<Long, Integer> map = redisson.getMap(getKey(resType, date));
        return map.readAllMap();
    }

    public boolean deleteMap(ActionType resType, String date) {
        RMap<Long, Integer> map = redisson.getMap(getKey(resType, date));
        return map.delete();
    }

    private String getKey(ActionType resType, String date) {
        StringBuilder sb =  new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(redisKeys.adPrefix)
        .append(date).append("-");
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
