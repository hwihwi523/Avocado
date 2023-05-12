package com.avocado.product.kafka;

import com.avocado.product.Click;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Bean
    public KafkaTemplate<Long, Click> ClickKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");
//            Properties configs = new Properties();
//            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
//            configs.setProperty("value.serializer", AvroSerializer.class.getName());
//            configs.setProperty("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");

//        props.put(JsonSerializer.TYPE_MAPPINGS,
//                "bid:com.dokidoki.bid.kafka.dto.KafkaBidDTO");

        ProducerFactory<Long, Click> ClickProducerFactory = new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(ClickProducerFactory);
    }
}
