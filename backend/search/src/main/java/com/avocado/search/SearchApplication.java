package com.avocado.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false"); // restart / launcher 클래스 로더 이슈 없애줌
        SpringApplication.run(SearchApplication.class, args);
    }

}
