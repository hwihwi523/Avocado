package com.avocado.userserver.kafka.service

import com.avocado.PurchaseHistory
import com.avocado.userserver.api.controller.OauthController
import com.avocado.userserver.api.service.ConsumerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val consumerService: ConsumerService
) {
    val log: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["\${spring.kafka.purchase-history-config.topic}"], containerFactory = "purchaseHistoryKafkaListenerContainerFactory")
    suspend fun purchaseHistoryListener(
        @Payload purchaseHistory: PurchaseHistory,
        @Headers headers: MessageHeaders) {

        log.info("Received purchase history message: [{}]", purchaseHistory)
        consumerService.updateWallet(purchaseHistory.totalPrice ,purchaseHistory.userId)

    }
}