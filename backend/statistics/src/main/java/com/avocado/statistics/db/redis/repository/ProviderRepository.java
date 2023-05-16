package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.api.response.ProviderStatisticsResp;
import com.avocado.statistics.common.codes.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ProviderRepository {
    private final RedissonClient client;
    private final RedisKeys redisKeys;

    /**
     * 판매자 통계 정보를 Redis에 Cache 하는 메서드
     * @param providerId : 스토어 ID
     * @param resp : 저장할 통계 정보
     */
    public void save(String providerId, ProviderStatisticsResp resp) {
        try {
            String redisKey = redisKeys.commPrefix + redisKeys.providerPrefix;  // st-provider
            RMapCache<String, ProviderStatisticsResp> map = client.getMapCache(redisKey);
            map.put(providerId, resp, 5L, TimeUnit.MINUTES);  // TTL 5분
        } catch (Exception ignored) {}
    }

    /**
     * Redis에서 판매자 통계 정보를 가져오는 메서드
     * @param providerId : 스토어 ID
     * @return : 해당 스토어의 통계 정보
     */
    public ProviderStatisticsResp load(String providerId) {
        try {
            String redisKey = redisKeys.commPrefix + redisKeys.providerPrefix;  // st-provider
            RMapCache<String, ProviderStatisticsResp> map = client.getMapCache(redisKey);
            return map.get(providerId);
        } catch (Exception ignored) {}
        return null;
    }
}
