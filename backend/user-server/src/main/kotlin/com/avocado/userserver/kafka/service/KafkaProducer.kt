package com.avocado.userserver.kafka.service

import com.avocado.MemberEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class KafkaProducer(
    private val memberEventKafkaTemplate: KafkaTemplate<String, MemberEvent>,
    @Value(value = "\${spring.kafka.member-event-config.topic}")
    val memberEventTopic: String
) {
    val log: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)

    fun sendMemberEvent(consumerId:String, memberEvent:MemberEvent) {
        val future: CompletableFuture<SendResult<String, MemberEvent>> =
            memberEventKafkaTemplate.send(memberEventTopic, consumerId, memberEvent)

        future.handle { result, ex ->
            if (ex != null) {
                log.warn("Unable to send message: [{}] due to : {}", memberEvent, ex.message)
            } else {
                log.info(
                    "click sent: [{}] with partition = [{}] offset=[{}]",
                    memberEvent,
                    result.recordMetadata.partition(),
                    result.recordMetadata.offset()
                )
            }
        }
    }

}