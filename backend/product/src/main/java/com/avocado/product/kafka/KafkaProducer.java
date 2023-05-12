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

    private final KafkaTemplate<Long, Click> clickKafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<Long, Click> clickKafkaTemplate) {
        this.clickKafkaTemplate = clickKafkaTemplate;
    }

    public void sendClick(Long merchandiseID, UUID consumerID) {
        Click click = Click.newBuilder()
                .setUserId(makeRandomUUID().toString())
                .build();
        ListenableFuture<SendResult<Long, Click>> future = clickKafkaTemplate.send(clickTopic, merchandiseID, click);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to send message: [{}] due to : {}", click, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Long, Click> result) {
                log.info("click sent: [{}] with partition = [{}] offset=[{}]", click, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }
        });
    }

    public UUID makeRandomUUID() {

        /*
        '24E258AF-5B31-4BF1-8268-8F0EFACB1F04', '임혜진'
        '2D2168DE-D05E-426E-A6A5-B4D7516BAA52', '오현규'
        '3AC71B16-28E7-4EF4-8438-AFD76F6CEACE', '김도원'
        'CD9C70A5-76D5-43FF-9A7B-A34A211F3240', '김범식'
        'F742BD5B-3AC2-4A57-93A5-006719728AB0', '윤재휘'
        */

        int randomIndex = (int) (Math.random() * 2);

        switch (randomIndex) {
            case 0:
                return UUID.fromString("24E258AF-5B31-4BF1-8268-8F0EFACB1F04");
            case 1:
                return UUID.fromString("2D2168DE-D05E-426E-A6A5-B4D7516BAA52");
//            case 2:
//                return UUID.fromString("3AC71B16-28E7-4EF4-8438-AFD76F6CEACE");
//            case 3:
//                return UUID.fromString("CD9C70A5-76D5-43FF-9A7B-A34A211F3240");
//            case 4:
//                return UUID.fromString("F742BD5B-3AC2-4A57-93A5-006719728AB0");
            default:
                throw new IllegalStateException("Unexpected value: " + randomIndex);
        }

    }
}
