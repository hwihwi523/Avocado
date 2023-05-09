package com.avocado.community.common.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler 테스트")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GlobalExceptionHandlerTest {

    @Test
    public void printStackTrace_테스트() {
        Exception e = new Exception("프린트 스택 트레이스 테스트");
        e.printStackTrace();

    }

}