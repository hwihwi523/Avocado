package com.avocado.commercial.kafka.service;


import com.avocado.AdStatus;
import com.avocado.commercial.Service.CommercialService;
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

    private CommercialService commercialService;

    @Autowired
    public KafkaConsumer(CommercialService commercialService){
        this.commercialService = commercialService;
    }


    @KafkaListener(topics = "${spring.kafka.ad-status-config.topic}", containerFactory = "adStatusKafkaListenerContainerFactory")
    public void resultListener(
            @Payload AdStatus adStatus,
            @Headers MessageHeaders headers,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) int today) {

        log.info("Received result message: [{}]", adStatus);
        log.info("Received today as key: [{}]", today);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });

        // do some logics
        commercialService.saveCommercialStatistic(adStatus, today);

    }
}
