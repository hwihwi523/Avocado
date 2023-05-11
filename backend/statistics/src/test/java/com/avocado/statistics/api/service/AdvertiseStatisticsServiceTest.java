package com.avocado.statistics.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AdvertiseStatisticsService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AdvertiseStatisticsServiceTest {

    @Autowired AdvertiseStatisticsService advertiseStatisticsService;

    @Test
    @DisplayName("광고 통계 결과 가져오기 테스트")
    public void adv_statistics_result() {
        advertiseStatisticsService.sendAdvertiseInfo();
    }

}