package com.avocado.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StatisticsApplication {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false"); // restart / launcher 클래스 로더 이슈 없애줌
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
