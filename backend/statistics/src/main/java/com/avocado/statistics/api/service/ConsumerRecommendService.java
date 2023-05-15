package com.avocado.statistics.api.service;

import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.redis.repository.ScoreRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsumerRecommendService {

    private final JwtUtils jwtUtils;
    private final ScoreRepository scoreRepository;


    public Object getConsumerRecommend(Claims claims) {
        UUID id = jwtUtils.getId(claims);
        String type = jwtUtils.getType(claims);

        return null;
    }
}
