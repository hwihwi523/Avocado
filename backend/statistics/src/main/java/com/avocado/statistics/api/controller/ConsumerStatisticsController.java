package com.avocado.statistics.api.controller;

import com.avocado.statistics.api.service.ConsumerRecommendService;
import com.avocado.statistics.api.service.ConsumerStatisticsService;
import com.avocado.statistics.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ConsumerStatisticsController {
    private final ConsumerStatisticsService consumerStatisticsService;
    private final ConsumerRecommendService consumerRecommendService;
    private final JwtUtils jwtUtils;

    @GetMapping("/merchandises/{merchandiseId}")
    public ResponseEntity<?> getStatisticsOfMerchandiseDetail(@PathVariable long merchandiseId) {
        return ResponseEntity.ok(consumerStatisticsService.getDetailStatistics(merchandiseId));
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendation(HttpServletRequest request) {
        Claims claims = jwtUtils.getClaims(request);
        return ResponseEntity.ok(consumerRecommendService.getConsumerRecommend(claims));
    }
}
