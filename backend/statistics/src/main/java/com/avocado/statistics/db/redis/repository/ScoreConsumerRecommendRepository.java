package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.common.codes.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScoreConsumerRecommendRepository {
    private final RedissonClient redisson;
    private final RedisKeys redisKeys;

    private String getKey(int ageGenderIndex, int personalColorId, int mbtiId) {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(redisKeys.recPrefix)
        .append(ageGenderIndex).append(":")
        .append(personalColorId).append(":")
        .append(mbtiId);
        return sb.toString();
    }

    private String getPattern() {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(redisKeys.recPrefix).append("*:*:*");
        return sb.toString();
    }

    public void save(int ageGenderIndex, int personalColorId, int mbtiId, Long merchandiseId, Long score) {
        RMap<Long, Long> map = redisson.getMap(getKey(ageGenderIndex, personalColorId, mbtiId));
        map.put(merchandiseId, score);
    }

    public Map<Long, Long> getMap(int ageGenderIndex, int personalColorId, int mbtiId) {
        RMap<Long, Long> map = redisson.getMap(getKey(ageGenderIndex, personalColorId, mbtiId));
        return map.readAllMap();
    }

    public void deleteAll() {
        for (CategoryType cType: CategoryType.values()) {
            String pattern = getPattern();
            Iterable<String> keysByPattern = redisson.getKeys().getKeysByPattern(pattern);
            for (String key: keysByPattern) {
                redisson.getList(key).delete();
            }
        }
    }

}
