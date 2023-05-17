package com.avocado.community.kafka.service;

import com.avocado.MemberEvent;
import com.avocado.community.api.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ConsumerService consumerService;

    @KafkaListener(topics = "${spring.kafka.member-event-config.topic}", containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload MemberEvent memberEvent,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String consumerId,
            @Headers MessageHeaders headers) {

        log.info("Received member event message: [{}]", memberEvent);

        consumerService.consumerEventListener(consumerId, memberEvent);

//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }
}
