//package com.avocado.streaming.utils;
//
//import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
//import io.confluent.kafka.serializers.KafkaAvroDeserializer;
//import org.apache.kafka.common.serialization.Deserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.confluent.kafka.serializers.KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG;
//
//import org.apache.kafka.common.annotation.InterfaceStability;
//import org.apache.kafka.common.header.Headers;
//import org.apache.kafka.common.serialization.Deserializer;
//
//import java.util.Map;
//
//import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
//import io.confluent.kafka.serializers.KafkaAvroDeserializer;
//
/**
 * A schema-registry aware deserializer for reading data in "specific Avro" format.
 *
 * <p>This deserializer assumes that the serialized data was written in the wire format defined at
 * http://docs.confluent.io/current/schema-registry/docs/serializer-formatter.html#wire-format.
 * It requires access to a Confluent Schema Registry endpoint, which you must
 * {@link SpecificAvroDeserializer#configure(Map, boolean)} via the parameter
 * "schema.registry.url".</p>
 *
 * <p>See {@link SpecificAvroSerializer} for its serializer counterpart.</p>
 */
//@InterfaceStability.Unstable
//public class SpecificAvroDeserializer<T extends org.apache.avro.specific.SpecificRecord>
//        implements Deserializer<T> {
//
//    private final KafkaAvroDeserializer inner;
//
//    public SpecificAvroDeserializer() {
//        inner = new KafkaAvroDeserializer();
//    }
//
//    /**
//     * For testing purposes only.
//     */
//    SpecificAvroDeserializer(final SchemaRegistryClient client) {
//        inner = new KafkaAvroDeserializer(client);
//    }
//
//    @Override
//    public void configure(final Map<String, ?> deserializerConfig,
//                          final boolean isDeserializerForRecordKeys) {
//        inner.configure(
//                ConfigurationUtils.withSpecificAvroEnabled(deserializerConfig),
//                isDeserializerForRecordKeys);
//    }
//
//    @Override
//    public T deserialize(final String topic, final byte[] bytes) {
//        return deserialize(topic, null, bytes);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T deserialize(final String topic, final Headers headers, final byte[] bytes) {
//        return (T) inner.deserialize(topic, headers, bytes);
//    }
//
//    @Override
//    public void close() {
//        inner.close();
//    }
//
//}
