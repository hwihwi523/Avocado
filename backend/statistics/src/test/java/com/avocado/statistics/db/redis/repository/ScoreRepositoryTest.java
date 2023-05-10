package com.avocado.statistics.db.redis.repository;

import com.avocado.statistics.kafka.dto.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("ScoreRepository 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScoreRepositoryTest {

    @Autowired
    ScoreRepository scoreRepository;

    @Test
    @DisplayName("작동 테스트")
    public void runningTest() {
        scoreRepository.save(CategoryType.AGE_GENDER, Type.CART, 1);
    }
}