package com.avocado.community.db.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@DisplayName("MyBatis ConsumerMapper 테스트")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConsumerMapperTest {

    @Resource
    ConsumerMapper consumerMapper;

    @Test
    public void 소비자조회() {
        System.out.println(consumerMapper.getConsumerList());
    }
}
