package com.avocado.userserver.kafka.utils

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Deserializer

class SpecificAvroDeserializer<T : SpecificRecord?> : Deserializer<T> {
    var inner: KafkaAvroDeserializer

    /**
     * Constructor used by Kafka Streams.
     */
    constructor() {
        inner = KafkaAvroDeserializer()
    }

    constructor(client: SchemaRegistryClient?) {
        inner = KafkaAvroDeserializer(client)
    }

    constructor(client: SchemaRegistryClient?, props: Map<String?, *>?) {
        inner = KafkaAvroDeserializer(client, props)
    }

    override fun configure(configs: Map<String, *>?, isKey: Boolean) {
        val effectiveConfigs: MutableMap<String, Any?> = HashMap(configs)
        effectiveConfigs[SPECIFIC_AVRO_READER_CONFIG] = true
        inner.configure(effectiveConfigs, isKey)
    }

    override fun deserialize(s: String, bytes: ByteArray): T {
        val deserialize = inner.deserialize(s, bytes)
        println(deserialize)
        return deserialize as T
    }

    override fun close() {
        inner.close()
    }
}