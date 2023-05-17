package com.avocado.userserver.kafka.utils

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Serializer

class SpecificAvroSerializer<T : SpecificRecord?> : Serializer<T> {
    var inner: KafkaAvroSerializer

    /**
     * Constructor used by Kafka Streams.
     */
    constructor() {
        inner = KafkaAvroSerializer()
    }

    constructor(client: SchemaRegistryClient?) {
        inner = KafkaAvroSerializer(client)
    }

    constructor(client: SchemaRegistryClient?, props: Map<String?, *>?) {
        inner = KafkaAvroSerializer(client, props)
    }

    override fun configure(configs: Map<String, *>?, isKey: Boolean) {
        val effectiveConfigs: MutableMap<String, Any?> = HashMap(configs)
        effectiveConfigs[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        inner.configure(effectiveConfigs, isKey)
    }

    override fun serialize(topic: String, record: T): ByteArray {
        return inner.serialize(topic, record)
    }

    override fun close() {
        inner.close()
    }
}