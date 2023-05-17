package com.avocado.commercial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommercialApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false"); // restart / launcher 클래스 로더 이슈 없애줌
        SpringApplication.run(CommercialApplication.class, args);
    }

}
