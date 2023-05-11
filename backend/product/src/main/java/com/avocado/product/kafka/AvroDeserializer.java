package com.avocado.product.kafka;

import lombok.extern.log4j.Log4j2;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Log4j2
public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer {

    protected final Class<T> targetType;

    public AvroDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        T returnObject = null;

        try {

            if (data != null) {
                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(targetType.newInstance().getSchema());
                Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
                returnObject = (T) datumReader.read(null, decoder);
            }
        } catch (Exception e) {
            log.error("Unable to Deserialize", e);
        }

        return returnObject;
    }

    @Override
    public void close() {

    }
}