package com.avocado.product.kafka.service;

import com.avocado.Merchandise;
import com.avocado.MemberEvent;
import com.avocado.PurchaseHistory;
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
import java.util.List;

@Slf4j
@Service
public class KafkaConsumer {
    @PersistenceContext
    private EntityManager em;

    @KafkaListener(topics = "${spring.kafka.purchase-history-config.topic}", containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    public void purchaseHistoryListener(
            @Payload PurchaseHistory purchaseHistory,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String purchasedId,
            @Headers MessageHeaders headers) {

        log.info("Received purchase history message: [{}]", purchaseHistory);
        bulkInsert(purchaseHistory, purchasedId);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }

    /**
     * 구매 완료 상품 bulk insert
     */
    @Transactional
    public void bulkInsert(PurchaseHistory purchaseHistory, String purchaseId) {
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
                + ", " + merchandise.getLeftover()
                + ", \"" + merchandise.getSize() + "\""
                +")";
    }

    @KafkaListener(topics = "${spring.kafka.member-event-config.topic}", containerFactory = "memberEventKafkaListenerContainerFactory")
    public void memberEventListener(
            @Payload MemberEvent memberEvent,
            @Headers MessageHeaders headers,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String userId) {

        log.info("Received user ID: {}", userId);
        log.info("Received member event message: [{}]", memberEvent);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });


        // do some logics


    }

}
