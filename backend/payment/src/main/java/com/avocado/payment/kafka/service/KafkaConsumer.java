package com.avocado.payment.kafka.service;


import com.avocado.MemberEvent;
import com.avocado.PurchaseHistory;
import com.avocado.SignupInfo;
import com.avocado.UpdateInfo;
import com.avocado.payment.config.UUIDUtil;
import com.avocado.payment.entity.Consumer;
import com.avocado.payment.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    @PersistenceContext
    private final EntityManager em;
    private final ConsumerRepository consumerRepository;
    private final UUIDUtil uuidUtil;

    @KafkaListener(topics = "${spring.kafka.member-event-config.topic}", containerFactory = "memberEventKafkaListenerContainerFactory")
    public void memberEventListener(
            @Payload MemberEvent memberEvent,
            @Headers MessageHeaders headers,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String userId) {

        log.info("Received user id as key: {}", userId);
        log.info("Received member event message: [{}]", memberEvent);

        // do some logics
        updateConsumer(memberEvent, userId);
    }

    @Transactional
    public void updateConsumer(MemberEvent memberEvent, String userId) {
        UUID consumerId = uuidUtil.joinByHyphen(userId.replace("-", ""));

        switch (memberEvent.getEvent()) {
            case SIGN_UP:
                Consumer newConsumer = Consumer.builder()
                        .id(consumerId)
                        .build();
                em.persist(newConsumer);
                break;

            case SIGN_OUT:
                consumerRepository.findById(consumerId).ifPresent(em::remove);
                break;
        }
    }
}
