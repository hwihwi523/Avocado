package com.avocado.product.kafka;

import com.avocado.product.Click;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

//import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Bean
    public KafkaTemplate<String, Click> ClickKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

//        props.put(JsonSerializer.TYPE_MAPPINGS,
//                "bid:com.dokidoki.bid.kafka.dto.KafkaBidDTO");

        ProducerFactory<String, Click> ClickProducerFactory = new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(ClickProducerFactory);
    }
}
