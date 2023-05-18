package com.avocado.statistics.api.service;

import com.avocado.statistics.db.redis.repository.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecommendCalculateServiceTest 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecommendCalculateServiceTest {

    @Autowired RecommendCalculateService recommendCalculateService;

    @Test
    public void 작동() {
        long l1 = System.currentTimeMillis();
        recommendCalculateService.calculateRecommend();

    }

}