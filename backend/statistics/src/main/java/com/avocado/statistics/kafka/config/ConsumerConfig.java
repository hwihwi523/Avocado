package com.avocado.statistics.kafka.config;

import com.avocado.Result;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public ConsumerFactory<Long, Result> resultConsumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SpecificAvroDeserializer.class);
        props.put("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");
        props.put("specific.avro.reader", "true");

        return new DefaultKafkaConsumerFactory<>(props,
                new LongDeserializer(),
                new SpecificAvroDeserializer<>());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Result> resultKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, Result> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(resultConsumerFactory("statistics-group"));
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

}
