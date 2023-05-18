package com.avocado.statistics.kafka.service;

import com.avocado.AdStatus;
import com.avocado.Status;
import com.avocado.statistics.common.utils.DateUtil;
import com.avocado.statistics.db.mysql.entity.jpa.Click;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class KafkaProducer {

    @Value("${spring.kafka.ad-status-config.topic}")
    String adStatusTopic;

    private final KafkaTemplate<Integer, AdStatus> adStatusKafkaTemplate;

    @Autowired
    public KafkaProducer(
            KafkaTemplate<Integer, AdStatus> adStatusKafkaTemplate) {
        this.adStatusKafkaTemplate = adStatusKafkaTemplate;
    }

    public void sendAdStatus(AdStatus adStatus) {
        log.info("sending adStatus: [{}]", adStatus);
        // date를 key로 쓰는 것은 좋지 않은 듯.
        ListenableFuture<SendResult<Integer, AdStatus>> future = adStatusKafkaTemplate.send(adStatusTopic, DateUtil.getUnixDate(), adStatus);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to send message: [{}] due to : {}", adStatus, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Integer, AdStatus> result) {
                log.info("ad status sent: [{}] with partition = [{}] offset=[{}]", adStatus, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }
        });
    }
}
