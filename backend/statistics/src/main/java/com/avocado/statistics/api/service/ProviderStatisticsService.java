package com.avocado.statistics.api.service;

import com.avocado.statistics.api.response.ProviderStatisticsResp;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderStatisticsService {

    private final JwtUtils jwtUtils;

    public ProviderStatisticsResp getStatisticsInfo(Claims claims) {
        String type = jwtUtils.getType(claims);
        // 권한이 provider 가 아니면 FORBIDDEN 에러 내기
        if (!type.equals("provider")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        UUID id = jwtUtils.getId(claims);


        return null;
    }




}
