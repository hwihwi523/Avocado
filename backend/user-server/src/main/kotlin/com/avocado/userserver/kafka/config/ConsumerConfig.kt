package com.avocado.userserver.kafka.config

import com.avocado.PurchaseHistory
import com.avocado.userserver.kafka.utils.SpecificAvroDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
@EnableKafka
class ConsumerConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null

    fun purchaseHistoryConsumerFactory(groupId: String?): ConsumerFactory<String?, PurchaseHistory?> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] =
            SpecificAvroDeserializer::class.java
        props["schema.registry.url"] =
            "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081"
        props["specific.avro.reader"] = "true"
        return DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            SpecificAvroDeserializer()
        )
    }

    @Bean
    fun purchaseHistoryKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, PurchaseHistory>? {
        val factory: ConcurrentKafkaListenerContainerFactory<String, PurchaseHistory> =
            ConcurrentKafkaListenerContainerFactory<String, PurchaseHistory>()
        factory.consumerFactory = purchaseHistoryConsumerFactory("product-group")
        factory.setConcurrency(3)
        factory.containerProperties.pollTimeout = 3000
        return factory
    }
}