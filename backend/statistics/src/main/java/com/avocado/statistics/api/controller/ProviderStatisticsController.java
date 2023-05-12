package com.avocado.statistics.api.controller;

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
    public ResponseEntity<?> getProviderStatisticsInfo(HttpServletRequest request) {
        Claims claims = jwtUtils.getClaims(request);
        return ResponseEntity.ok(providerStatisticsService.getStatisticsInfo(claims));
    }
}
