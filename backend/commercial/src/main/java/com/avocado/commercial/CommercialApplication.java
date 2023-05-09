package com.avocado.commercial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommercialApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommercialApplication.class, args);
    }

}
