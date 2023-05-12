package com.avocado.statistics.api.controller;

import com.avocado.statistics.api.response.BaseResp;
import com.avocado.statistics.api.response.ProviderStatisticsResp;
import com.avocado.statistics.api.service.ProviderStatisticsService;
import com.avocado.statistics.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ProviderStatisticsController {

    private final JwtUtils jwtUtils;
    private final ProviderStatisticsService providerStatisticsService;

    @GetMapping("/provider")
    public ResponseEntity<BaseResp> getProviderStatisticsInfo(HttpServletRequest request) {
        Claims claims = jwtUtils.getClaims(request);
        ProviderStatisticsResp resp = providerStatisticsService.getStatisticsInfo(claims);
        return ResponseEntity.ok(BaseResp.of("판매자 통계 정보 조회 성공", resp));
    }
}
