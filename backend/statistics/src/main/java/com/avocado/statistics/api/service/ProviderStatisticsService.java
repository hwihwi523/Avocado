package com.avocado.statistics.api.service;

import com.avocado.statistics.api.response.ProviderStatisticsResp;
import com.avocado.statistics.common.error.BaseException;
import com.avocado.statistics.common.error.ResponseCode;
import com.avocado.statistics.common.utils.JwtUtils;
import com.avocado.statistics.db.mysql.repository.dto.SellCountTotalRevenueDTO;
import com.avocado.statistics.db.mysql.repository.jpa.StatisticsRepository;
import com.avocado.statistics.db.redis.repository.AdvertiseCountRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderStatisticsService {
    private final JwtUtils jwtUtils;
    private final StatisticsRepository jpaStatisticsRepository;

    public ProviderStatisticsResp getStatisticsInfo(Claims claims) {
        String type = jwtUtils.getType(claims);
        // 권한이 provider 가 아니면 FORBIDDEN 에러 내기
        if (!type.equals("provider")) {
            throw new BaseException(ResponseCode.FORBIDDEN);
        }

        // 조회수, 판매수, 총 판매액, 상품수 조회
        UUID providerId = jwtUtils.getId(claims);
        Long clickCount = jpaStatisticsRepository.getClickCount(providerId);  // 조회수
        SellCountTotalRevenueDTO sellCountTotalRevenueDTO = jpaStatisticsRepository
                .getSellCountTotalRevenue(providerId);  // 판매수, 총 판매액
        Long merchandiseCount = jpaStatisticsRepository.getMerchandiseCount(providerId);

        // Response DTO 생성
        ProviderStatisticsResp providerStatisticsResp = new ProviderStatisticsResp();
        providerStatisticsResp.updateNumericStatistics(
                clickCount,
                sellCountTotalRevenueDTO.getSellCount(),
                sellCountTotalRevenueDTO.getTotalRevenue(),
                merchandiseCount
        );

        return providerStatisticsResp;
    }
}
