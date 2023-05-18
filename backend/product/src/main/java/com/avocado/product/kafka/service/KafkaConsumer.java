package com.avocado.product.kafka.service;

import com.avocado.*;
import com.avocado.product.config.UUIDUtil;
import com.avocado.product.entity.Consumer;
import com.avocado.product.entity.Mbti;
import com.avocado.product.entity.PersonalColor;
import com.avocado.product.entity.Purchase;
import com.avocado.product.exception.DataManipulationException;
import com.avocado.product.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    @PersistenceContext
    private final EntityManager em;
    private final UUIDUtil uuidUtil;
    private final ConsumerRepository consumerRepository;

    @Transactional
    @KafkaListener(topics = "${spring.kafka.purchase-history-config.topic}", containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload PurchaseHistory purchaseHistory,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String purchaseId,
            @Headers MessageHeaders headers) {

        log.info("Received purchase history message: [{}]", purchaseHistory);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics
        bulkInsert(purchaseHistory, purchaseId);
    }

    /**
     * 구매 완료 상품 bulk insert
     */
    @Transactional
    public void bulkInsert(PurchaseHistory purchaseHistory, String purchaseId) {
        try {
            // 구매내역 넣기
            Consumer proxyConsumer = consumerRepository.getOne(uuidUtil.joinByHyphen(purchaseHistory.getUserId()));

            Purchase purchase = Purchase.builder()
                    .id(uuidUtil.joinByHyphen(purchaseId))
                    .consumer(proxyConsumer)
                    .totalPrice(purchaseHistory.getTotalPrice())
                    .createdAt(LocalDateTime.parse(purchaseHistory.getCreatedAt()))
                    .build();
            em.persist(purchase);
        } catch (DataManipulationException ignored) { return; }  // 이미 존재할 경우 메서드 종료

        List<Merchandise> merchandises = purchaseHistory.getMerchandises();

        // 상품 ID, 구매대기내역 ID 모두 내부 데이터를 사용하기 때문에 sql injection 문제 없을 듯 (아마)
        String bulkQuery = "INSERT INTO purchased_merchandise (merchandise_id, purchase_id, provider_id, price, quantity, size) " +
                "VALUES " + getValue(purchaseId, merchandises.get(0));
        for (int i = 1; i < merchandises.size(); i++)
            bulkQuery += ", " + getValue(purchaseId, merchandises.get(i));
        log.info("Insert purchase history: " + bulkQuery);
        em.createNativeQuery(bulkQuery).executeUpdate();
    }
    private String getValue(String purchaseId, Merchandise merchandise) {
        return "("+ merchandise.getMerchandiseId()
                + ", UNHEX(\"" + purchaseId + "\")"
                + ", UNHEX(\"" + merchandise.getProviderId() + "\")"
                + ", " + merchandise.getPrice()
                + ", " + merchandise.getQuantity()
                + ", \"" + merchandise.getSize() + "\""
                +")";
    }

    @Transactional
    @KafkaListener(topics = "${spring.kafka.member-event-config.topic}", containerFactory = "memberEventKafkaListenerContainerFactory")
    public void memberEventListener(
            @Payload MemberEvent memberEvent,
            @Headers MessageHeaders headers,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String userId) {

        log.info("Received user ID: {}", userId);
        log.info("Received member event message: [{}]", memberEvent);

        // do some logics
        updateConsumer(memberEvent, userId);
    }

    @Transactional
    public void updateConsumer(MemberEvent memberEvent, String userId) {
        UUID consumerId = uuidUtil.joinByHyphen(userId.replace("-", ""));
        UpdateInfo updateInfo = memberEvent.getUpdateInfo();
        SignupInfo signupInfo = memberEvent.getSignupInfo();

        // 회원가입, 정보 수정에서 사용될 프록시 객체 MBTI, Personal Color
        Mbti proxyMbti = em.getReference(Mbti.class, updateInfo.getMbtiId());
        PersonalColor proxyPersonalColor = em.getReference(PersonalColor.class, updateInfo.getPersonalColorId());

        switch (memberEvent.getEvent()) {
            case SIGN_UP:
                Consumer newConsumer = Consumer.builder()
                        .id(consumerId)
                        .age((short)updateInfo.getAgeGroup())
                        .gender(updateInfo.getGender())
                        .mbti(proxyMbti)
                        .personalColor(proxyPersonalColor)
                        .pictureUrl(signupInfo.getPictureUrl())
                        .name(signupInfo.getConsumerName())
                        .build();
                em.persist(newConsumer);
                break;

            case SIGN_OUT:
                Consumer removeConsumer = consumerRepository.findById(consumerId);
                if (removeConsumer != null)
                    em.remove(removeConsumer);
                break;

            case UPDATE:
                Consumer updateConsumer = consumerRepository.findById(consumerId);
                if (updateConsumer != null) {
                    updateConsumer.update(
                            proxyPersonalColor,
                            proxyMbti,
                            signupInfo.getConsumerName(),
                            signupInfo.getPictureUrl(),
                            updateConsumer.getGender(),
                            updateConsumer.getAge()
                    );
                }
                break;
        }
    }
}
