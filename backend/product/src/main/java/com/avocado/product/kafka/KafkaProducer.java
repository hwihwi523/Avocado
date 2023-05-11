package com.avocado.product.kafka;

import com.avocado.product.Click;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@Slf4j
public class KafkaProducer {

    @Value(value = "${spring.kafka.click-config.topic}")
    private String clickTopic;

    private final KafkaTemplate<String, Click> clickKafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Click> clickKafkaTemplate) {
        this.clickKafkaTemplate = clickKafkaTemplate;
    }

    public void sendClick(Click click, UUID consumerId) {
        ListenableFuture<SendResult<String, Click>> future = clickKafkaTemplate.send(clickTopic, click);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to send message: [{}] due to : {}", click, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Click> result) {
                log.info("click sent: [{}] with partition = [{}] offset=[{}]", click, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }
        });
    }
}
