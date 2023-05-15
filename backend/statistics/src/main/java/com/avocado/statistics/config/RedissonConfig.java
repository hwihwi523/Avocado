package com.avocado.statistics.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    /*
        RedissonClient 로 bean 을 가져와 사용하면 됨
        shutdown : 빈 삭제 시 redisson instance 는 닫아버리지만,
        redis 서버는 닫지 않음
     */
    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonSingle(@Value("classpath:/redisson-single.yaml") Resource configFile) throws IOException {
        Config config = Config.fromYAML(configFile.getInputStream());
        return Redisson.create(config);
    }

//    @Bean(destroyMethod = "shutdown")
//    RedissonClient redissonMasterSlave(@Value("classpath:/redisson-ms.yaml") Resource configFile) throws IOException {
//        Config config = Config.fromYAML(configFile.getInputStream());
//        return Redisson.create(config);
//    }

}