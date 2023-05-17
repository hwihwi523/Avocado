package com.avocado.payment.kafka.service;


import com.avocado.MemberEvent;
import com.avocado.PurchaseHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @KafkaListener(topics = "${spring.kafka.member-event-config.topic}", containerFactory = "memberEventKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload MemberEvent memberEvent,
            @Headers MessageHeaders headers,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String userId) {

        log.info("Received user id as key: {}", userId);
        log.info("Received member event message: [{}]", memberEvent);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }
}
