package com.avocado.search.kafka.service;

import com.avocado.CompactReview;
import com.avocado.PurchaseHistory;
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
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.purchase-history-config.topic}", containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload PurchaseHistory purchaseHistory,
            @Headers MessageHeaders headers) {

        log.info("Received new review message: [{}]", purchaseHistory);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }

    @KafkaListener(topics = "${spring.kafka.review-config.topic}", containerFactory = "compactReviewKafkaListenerContainerFactory")
    public void reviewListener(
            @Payload CompactReview compactReview,
            @Headers MessageHeaders headers) {

        log.info("Received purchase history message: [{}]", compactReview);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }

}