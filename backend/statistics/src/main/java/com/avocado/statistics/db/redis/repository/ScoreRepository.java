package com.avocado.statistics.db.redis.repository;

import com.avocado.ActionType;
import com.avocado.statistics.common.codes.RedisKeys;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.common.utils.CategoryTypeUtil;

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

    private String getKey(long merchandiseId, CategoryType cType, ActionType resType) {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(getCategoryPrefix(cType))
        .append(getResultTypePrefix(resType))
        .append(merchandiseId);

        return sb.toString();
    }

    private String getPattern(CategoryType cType, ActionType resType) {
        StringBuilder sb = new StringBuilder();
        sb.append(redisKeys.commPrefix)
        .append(getCategoryPrefix(cType))
        .append(getResultTypePrefix(resType))
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

    private String getResultTypePrefix(ActionType resType) {
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

    public void save(CategoryType cType, ActionType resType, Long merchandiseId, int id) {
        RMap<Integer, Long> map = redisson.getMap(getKey(merchandiseId, cType, resType));
        map.putIfAbsent(id, 0L);
        Long cnt = map.get(id);
        map.put(id, cnt + 1);
    }

    public Map<Integer, Long> getMapByMerchandiseId(CategoryType cType, ActionType resType, Long merchandiseId) {
        RMap<Integer, Long> map = redisson.getMap(getKey(merchandiseId, cType, resType));
        return map.readAllMap();
    }

    public List<Long> getByMerchandiseId(CategoryType cType, ActionType resType, Long merchandiseId) {
        RMap<Integer, Long> map = redisson.getMap(getKey(merchandiseId, cType, resType));
        int varSize = categoryTypeUtil.getVarSize(cType);

        List<Long> resList = new ArrayList<>();
        for (int i = 0; i < varSize; i++) {
            resList.add(map.getOrDefault(i, 0L));
        }
        return resList;
    }

    public void deleteAll() {
        CategoryType[] cTypes = CategoryType.values();
        ActionType[] resTypes = { ActionType.VIEW, ActionType.CLICK, ActionType.CART, ActionType.LIKE, ActionType.PAYMENT };
        for (CategoryType cType: cTypes) {
            for(ActionType resType: resTypes) {
                String pattern = getPattern(cType, resType);
                Iterable<String> keysByPattern = redisson.getKeys().getKeysByPattern(pattern);
                for (String key: keysByPattern) {
                    redisson.getMap(key).delete();
                }
            }
        }
    }
}
