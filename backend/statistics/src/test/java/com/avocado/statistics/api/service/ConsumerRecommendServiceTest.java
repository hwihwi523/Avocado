package com.avocado.statistics.api.service;

import com.avocado.statistics.api.dto.ScoreResult;
import com.avocado.statistics.db.mysql.entity.mybatis.Consumer;
import com.avocado.statistics.db.mysql.repository.mybatis.ConsumerRepository;
import com.avocado.statistics.db.redis.repository.CategoryType;
import com.avocado.statistics.db.redis.repository.MerchandiseIdSetRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConsumerRecommendService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ConsumerRecommendServiceTest {

    @Autowired private ConsumerRecommendService consumerRecommendService;
    @Autowired private ConsumerRepository consumerRepository;
    @Autowired private MerchandiseIdSetRepository merchandiseIdSetRepository;

    String userId = "24E258AF-5B31-4BF1-8268-8F0EFACB1F04";


    @Test
    public void 작동테스트() {
        Consumer consumer = consumerRepository.getById(UUID.fromString(userId)).get();
        BitSet bitSet = merchandiseIdSetRepository.getBitSet();

        List<CategoryType> cTypes = new ArrayList<>();
        cTypes.add(CategoryType.PERSONAL_COLOR);


        List<ScoreResult> scoreResults = consumerRecommendService.calculateRecommend(consumer, cTypes, bitSet);
        System.out.println(scoreResults);
    }

}