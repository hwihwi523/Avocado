//package com.avocado.streaming.utils;
//
//import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
//import io.confluent.kafka.serializers.KafkaAvroDeserializer;
//import org.apache.avro.generic.GenericRecord;
//import org.apache.kafka.common.annotation.InterfaceStability;
//import org.apache.kafka.common.header.Headers;
//import org.apache.kafka.common.serialization.Deserializer;
//
//import java.util.Map;
//
///**
// * A schema-registry aware deserializer for reading data in "generic Avro" format.
// *
// * <p>This deserializer assumes that the serialized data was written in the wire format defined at
// * http://docs.confluent.io/current/schema-registry/docs/serializer-formatter.html#wire-format.
// * It requires access to a Confluent Schema Registry endpoint, which you must
// * {@link GenericAvroDeserializer#configure(Map, boolean)} via the parameter
// * "schema.registry.url".</p>
// *
// * <p>See {@link GenericAvroSerializer} for its serializer counterpart.</p>
// */
//@InterfaceStability.Unstable
//public class GenericAvroDeserializer implements Deserializer<GenericRecord> {
//
//    private final KafkaAvroDeserializer inner;
//
//    public GenericAvroDeserializer() {
//        inner = new KafkaAvroDeserializer();
//    }
//
//    /**
//     * For testing purposes only.
//     */
//    GenericAvroDeserializer(final SchemaRegistryClient client) {
//        inner = new KafkaAvroDeserializer(client);
//    }
//
//    @Override
//    public void configure(final Map<String, ?> deserializerConfig,
//                          final boolean isDeserializerForRecordKeys) {
//        inner.configure(deserializerConfig, isDeserializerForRecordKeys);
//    }
//
//    @Override
//    public GenericRecord deserialize(final String topic, final byte[] bytes) {
//        return deserialize(topic, null, bytes);
//    }
//
//    @Override
//    public GenericRecord deserialize(final String topic, final Headers headers, final byte[] bytes) {
//        return (GenericRecord) inner.deserialize(topic, headers, bytes);
//    }
//
//    @Override
//    public void close() {
//        inner.close();
//    }
//
//}
//
