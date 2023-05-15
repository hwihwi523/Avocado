package com.avocado.statistics.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProviderDBServiceTest 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProviderDBServiceTest {

    @Autowired ProviderDBService providerDBService;

    @Test
    public void 작동테스트() {
        providerDBService.update();

    }

}