package com.avocado.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CommunityApplication {
    public static void main(String[] args) throws IOException {
        System.setProperty("spring.devtools.restart.enabled", "false"); // restart / launcher 클래스 로더 이슈 없애줌
        SpringApplication.run(CommunityApplication.class, args);
    }
}
