package com.avocado.statistics.api.controller;

import com.avocado.statistics.api.service.ConsumerStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsumerStatisticsController {
    private final ConsumerStatisticsService consumerStatisticsService;

    @GetMapping("/merchandises/{merchandiseId}")
    public ResponseEntity<?> getStatisticsOfMerchandiseDetail(@PathVariable long merchandiseId) {
        return ResponseEntity.ok(consumerStatisticsService.getDetailStatistics(merchandiseId));
    }
}
