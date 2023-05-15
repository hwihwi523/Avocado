package com.avocado.statistics.api.service;

import com.avocado.statistics.kafka.dto.Result;
import com.avocado.statistics.kafka.dto.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StreamService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StreamServiceTest {

    @Autowired StreamService streamService;

    String userId = "24E258AF-5B31-4BF1-8268-8F0EFACB1F04";

    @Test
    @DisplayName("광고 DTO INSERT 테스트")
    public void insert_adv_dto() {
        Type[] values = {Type.AD_CLICK, Type.AD_VIEW, Type.AD_PAYMENT};
        for(long id = 0; id < 20; id++) {
            int size = new Random().nextInt(10) + 1;
            for (int i = 0; i < size; i++) {
                Result result = new Result(userId, id, values[new Random().nextInt(3)]);
                streamService.consumeResult(result);
            }
        }
    }

    @Test
    @DisplayName("점수 DTO INSERT 테스트")
    public void insert_score_dto() {
        Type[] values = {Type.CART, Type.CLICK, Type.LIKE, Type.PAYMENT, Type.VIEW};
        for(long id = 0; id < 20; id++) {
            int size = new Random().nextInt(10) + 1;
            for (int i = 0; i < size; i++) {
                Result result = new Result(userId, id, values[new Random().nextInt(values.length)]);
                streamService.consumeResult(result);
            }
        }
    }
}