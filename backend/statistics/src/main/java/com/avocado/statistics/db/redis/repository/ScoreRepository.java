package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.common.codes.RedisKeys;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.common.utils.CategoryTypeUtil;
import com.avocado.statistics.kafka.dto.Type;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScoreRepository {

    private final RedissonClient redisson;
    private final RedisKeys redisKeys;
    private final CategoryTypeUtil categoryTypeUtil;

    private String getKey(long merchandiseId, CategoryType cType, Type resType) {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(getCategoryPrefix(cType))
        .append(getResultTypePrefix(resType))
        .append(merchandiseId);

        return sb.toString();
    }

    private String getCategoryPrefix(CategoryType cType) {
        switch(cType) {
            case AGE_GENDER:
                return redisKeys.ageGenderPrefix;
            case MBTI:
                return redisKeys.mbtiPrefix;
            case PERSONAL_COLOR:
                return redisKeys.personalColorPrefix;
            default:
                throw new BaseException(ResponseCode.INVALID_VALUE);
        }
    }

    private String getResultTypePrefix(Type resType) {
        switch (resType) {
            case VIEW:
                return redisKeys.viewPrefix;
            case CLICK:
                return redisKeys.clickPrefix;
            case LIKE:
                return redisKeys.likePrefix;
            case CART:
                return redisKeys.cartPrefix;
            case PAYMENT:
                return redisKeys.paymentPrefix;
            default:
                throw new BaseException(ResponseCode.INVALID_VALUE);
        }
    }

    public void save(CategoryType cType, Type resType, Long merchandiseId, int id) {
        RMap<Integer, Long> map = redisson.getMap(getKey(merchandiseId, cType, resType));
        map.putIfAbsent(id, 0L);
        Long cnt = map.get(id);
        map.put(id, cnt + 1);
    }

    public Map<Integer, Long> getMapByMerchandiseId(CategoryType cType, Type resType, Long merchandiseId) {
        RMap<Integer, Long> map = redisson.getMap(getKey(merchandiseId, cType, resType));
        return map.readAllMap();
    }

    public List<Long> getByMerchandiseId(CategoryType cType, Type resType, Long merchandiseId) {
        RMap<Integer, Long> map = redisson.getMap(getKey(merchandiseId, cType, resType));
        int varSize = categoryTypeUtil.getVarSize(cType);

        List<Long> resList = new ArrayList<>();
        for (int i = 0; i < varSize; i++) {
            resList.add(map.getOrDefault(i, 0L));
        }
        return resList;
    }
}
