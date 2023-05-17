package com.avocado.product.kafka.config;

import com.avocado.CartAdd;
import com.avocado.Click;
import com.avocado.CompactReview;
import com.avocado.View;
import com.avocado.product.entity.Cart;
import com.avocado.product.kafka.utils.SpecificAvroSerializer;
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
//        props.put("specific.avro.reader", "true");

        ProducerFactory<Long, Click> ClickProducerFactory = new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(ClickProducerFactory);
    }

    @Bean
    public KafkaTemplate<Long, View> ViewKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");
//        props.put("specific.avro.reader", "true");

        ProducerFactory<Long, View> viewProducerFactory = new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(viewProducerFactory);
    }

    @Bean
    public KafkaTemplate<Long, CartAdd> CartKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");
//        props.put("specific.avro.reader", "true");

        ProducerFactory<Long, CartAdd> cartProducerFactory = new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(cartProducerFactory);
    }

    public ProducerFactory<Long, CompactReview> CompactReviewProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SpecificAvroSerializer.class);
        props.put("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");
        props.put("specific.avro.reader", "true");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<Long, CompactReview> CompactReviewKafkaTemplate() {
        return new KafkaTemplate<>(CompactReviewProducerFactory());
    }
}
