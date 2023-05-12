package com.avocado.commercial.kafka;

import com.avocado.commercial.Adview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Slf4j
@Service
public class KafkaProducer {

    @Value(value = "${spring.kafka.ad-config.topic}")
    private String adTopic;

    private final KafkaTemplate<Long, Adview> adviewKafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<Long, Adview> adviewKafkaTemplate) {
        this.adviewKafkaTemplate = adviewKafkaTemplate;
    }

    public void sendAdview(Long adId, UUID userId) {
        Adview adview = Adview.newBuilder().setUserId(userId.toString()).build();
        ListenableFuture<SendResult<Long, Adview>> future = adviewKafkaTemplate.send(adTopic, adId, adview);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to send message: [{}] due to : {}", adview, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Long, Adview> result) {
                log.info("adview sent: [{}] with partition = [{}] offset=[{}]", adview, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }
        });
    }
}
