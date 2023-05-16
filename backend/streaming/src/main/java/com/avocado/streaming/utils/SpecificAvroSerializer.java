//package com.avocado.streaming.utils;
//
//import org.apache.kafka.common.annotation.InterfaceStability;
//import org.apache.kafka.common.header.Headers;
//import org.apache.kafka.common.serialization.Serializer;
//
//import java.util.Map;
//
//import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
//import io.confluent.kafka.serializers.KafkaAvroSerializer;
//
///**
// * A schema-registry aware serializer for writing data in "specific Avro" format.
// *
// * <p>This serializer writes data in the wire format defined at
// * http://docs.confluent.io/current/schema-registry/docs/serializer-formatter.html#wire-format.
// * It requires access to a Confluent Schema Registry endpoint, which you must
// * {@link SpecificAvroSerializer#configure(Map, boolean)} via the parameter
// * "schema.registry.url".</p>
// *
// * <p>See {@link SpecificAvroDeserializer} for its deserializer counterpart.</p>
// */
//@InterfaceStability.Unstable
//public class SpecificAvroSerializer<T extends org.apache.avro.specific.SpecificRecord>
//        implements Serializer<T> {
//
//    private final KafkaAvroSerializer inner;
//
//    public SpecificAvroSerializer() {
//        inner = new KafkaAvroSerializer();
//    }
//
//    /**
//     * For testing purposes only.
//     */
//    SpecificAvroSerializer(final SchemaRegistryClient client) {
//        inner = new KafkaAvroSerializer(client);
//    }
//
//    @Override
//    public void configure(final Map<String, ?> serializerConfig,
//                          final boolean isSerializerForRecordKeys) {
//        inner.configure(
//                ConfigurationUtils.withSpecificAvroEnabled(serializerConfig),
//                isSerializerForRecordKeys);
//    }
//
//    @Override
//    public byte[] serialize(final String topic, final T record) {
//        return serialize(topic, null, record);
//    }
//
//    @Override
//    public byte[] serialize(final String topic, final Headers headers, final T record) {
//        return inner.serialize(topic, headers, record);
//    }
//
//    @Override
//    public void close() {
//        inner.close();
//    }
//
//}