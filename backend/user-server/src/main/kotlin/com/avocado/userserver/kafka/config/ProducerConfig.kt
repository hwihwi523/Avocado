package com.avocado.userserver.kafka.config

import com.avocado.MemberEvent
import com.avocado.userserver.kafka.utils.SpecificAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class ProducerConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null

    fun MemberEventProducerFactory(): ProducerFactory<String, MemberEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = SpecificAvroSerializer::class.java
        props["schema.registry.url"] = "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081"
        props["specific.avro.reader"] = "true"
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun MemberEventTemplate(): KafkaTemplate<String, MemberEvent> {
        return KafkaTemplate(MemberEventProducerFactory())
    }
}