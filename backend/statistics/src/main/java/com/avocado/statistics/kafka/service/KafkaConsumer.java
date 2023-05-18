package com.avocado.statistics.kafka.service;

import com.avocado.Result;
import com.avocado.statistics.api.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    StreamService streamService;

    @KafkaListener(topics = "${spring.kafka.result-config.topic}", containerFactory = "resultKafkaListenerContainerFactory")
    public void resultListener(
            @Payload Result result,
            @Headers MessageHeaders headers,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Long merchandiseId) {

        log.info("Received result message: [{}], merchandiseId: [{}]", result, merchandiseId);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });

        // do some logics
         streamService.consumeResult(result, merchandiseId);

    }
}
