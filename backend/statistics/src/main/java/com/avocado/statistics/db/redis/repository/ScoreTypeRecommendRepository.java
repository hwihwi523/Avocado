package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.common.codes.RedisKeys;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScoreTypeRecommendRepository {

    private final RedissonClient redisson;
    private final RedisKeys redisKeys;

    private String getKey(CategoryType cType, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(redisKeys.recPrefix)
        .append(getCategoryPrefix(cType))
        .append(index);
        return sb.toString();
    }

    private String getPattern(CategoryType cType) {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(redisKeys.recPrefix)
        .append(getCategoryPrefix(cType))
        .append("*");
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

    public void save(CategoryType cType, int index, Long merchandiseId, Long score) {
        RMap<Long, Long> map = redisson.getMap(getKey(cType, index));
        map.put(merchandiseId, score);
    }

    public Map<Long, Long> getMap(CategoryType cType, int index) {
        RMap<Long, Long> map = redisson.getMap(getKey(cType, index));
        return map.readAllMap();
    }

    public void deleteByCategoryType(CategoryType cType) {
        String pattern = getPattern(cType);
        Iterable<String> keysByPattern = redisson.getKeys().getKeysByPattern(pattern);
        for (String key: keysByPattern) {
            redisson.getList(key).delete();
        }
    }

    public void deleteAll() {
        for (CategoryType cType: CategoryType.values()) {
            String pattern = getPattern(cType);
            Iterable<String> keysByPattern = redisson.getKeys().getKeysByPattern(pattern);
            for (String key: keysByPattern) {
                redisson.getList(key).delete();
            }
        }
    }

}
