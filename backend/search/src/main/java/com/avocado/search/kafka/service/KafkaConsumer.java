package com.avocado.search.kafka.service;

import com.avocado.CompactReview;
import com.avocado.PurchaseHistory;
import com.avocado.search.Service.SearchService;
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

    private SearchService searchService;

    @Autowired
    public KafkaConsumer(SearchService searchService){
        this.searchService = searchService;
    }

    @KafkaListener(topics = "${spring.kafka.purchase-history-config.topic}", containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload PurchaseHistory purchaseHistory,
            @Headers MessageHeaders headers) {

        log.info("Received new purchase message: [{}]", purchaseHistory);
        searchService.modifyProductInventory(purchaseHistory);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }

    @KafkaListener(topics = "${spring.kafka.review-config.topic}", containerFactory = "compactReviewKafkaListenerContainerFactory")
    public void reviewListener(
            @Payload CompactReview compactReview,
            @Headers MessageHeaders headers) {

        log.info("Received review history message: [{}]", compactReview);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });
        searchService.modifyProductReview(compactReview);

        // do some logics


    }

}