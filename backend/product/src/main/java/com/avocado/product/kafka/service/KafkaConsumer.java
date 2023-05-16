package com.avocado.product.kafka.service;

import com.avocado.PurchaseHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.purchase-history-config.topic}", containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload PurchaseHistory purchaseHistory,
            @Headers MessageHeaders headers) {

        log.info("Received purchase history message: [{}]", purchaseHistory);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }
}
