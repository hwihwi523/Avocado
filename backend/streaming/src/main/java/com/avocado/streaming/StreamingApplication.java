package com.avocado.streaming;

import com.avocado.Merchandise;
import com.avocado.PurchaseHistory;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

import com.avocado.Result;
import com.avocado.ActionType;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@SpringBootApplication
public class StreamingApplication {
	private static String applicationId = "streaming-v2";
	private static String bootstrapServer = "a62e5b172168c40419f9a1af18763a94-214776296.ap-northeast-2.elb.amazonaws.com:9094";
	private static Integer numStreamThreads = 1;
	private static Integer commitInterval = 10*1000;

	public static void main(String[] args) throws IOException {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(StreamingApplication.class, args);
		final Logger log = LogManager.getLogger(StreamingApplication.class);

		final Serde<String> stringSerde = Serdes.String();
		final Serde<Long> longSerde = Serdes.Long();

		boolean isKeySerde = false;

		final Serde<GenericRecord> genericAvroSerde = new GenericAvroSerde();
 		genericAvroSerde.configure(Collections.singletonMap("schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081"), isKeySerde);

		 final Serde<Result> resultSerde = new SpecificAvroSerde<>();
		 resultSerde.configure(Collections.singletonMap(
				 "schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081"), isKeySerde);


		 final Serde<PurchaseHistory> purchaseHistorySerde = new SpecificAvroSerde<>();
		purchaseHistorySerde.configure(Collections.singletonMap(
				"schema.registry.url", "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081"), isKeySerde);


		Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);

		streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, numStreamThreads);
//		streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, commitInterval);

		streamsConfiguration.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://a5ef82e13fcbc44689f93c4924981608-494875664.ap-northeast-2.elb.amazonaws.com:8081");
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, longSerde.getClass());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);

		StreamsBuilder builder = new StreamsBuilder();


		// payment counter
		KStream<String, PurchaseHistory> purchaseHistoryStream = builder.stream("test-purchase-history1", Consumed.with(stringSerde, purchaseHistorySerde));
		KStream<Long, Result> paymentStream = purchaseHistoryStream
				.flatMap((key, value) -> {
					List<KeyValue<Long, Result>> result = new ArrayList<>();
					for (Merchandise merchandise : value.getMerchandises()) {
						for (int i=0; i< merchandise.getQuantity(); i++) {
							result.add(KeyValue.pair(merchandise.getMerchandiseId(), Result.newBuilder()
									.setUserId(value.getUserId())
									.setAction(ActionType.PAYMENT)
									.setTimestamp(System.currentTimeMillis())
									.build()));
						}
					}
					return result;
				});

		paymentStream.foreach((key, value) -> log.info("purchase result stream >> key: {}, value: {}", key, value));
		paymentStream.to("test-result1", Produced.with(longSerde, resultSerde));

		// view counter
		KStream<Long, GenericRecord> viewStream = builder.stream("test-view", Consumed.with(longSerde, genericAvroSerde));
		KStream<Long, Result> viewResult = viewStream.map((key, value) -> KeyValue.pair(key, Result.newBuilder()
				.setUserId(value.get("userId").toString())
				.setAction(ActionType.VIEW)
				.build()));
		viewResult.foreach((key, value) -> log.info("View Stream >> key: {}, value: {}", key, value));
		viewResult.to("test-result1", Produced.with(longSerde, resultSerde));

		// click counter
		KStream<Long, GenericRecord> clickStream = builder.stream("test-click1", Consumed.with(longSerde, genericAvroSerde));
		KStream<Long, Result> resultStream = clickStream.map(((key, value) -> KeyValue.pair(key, Result.newBuilder()
				.setUserId(value.get("userId").toString())
				.setAction(ActionType.CLICK)
				.build())));
		resultStream.foreach((key, value) -> log.info("Click Stream >> key: {}, value: {}", key, value));
		resultStream.to("test-result1", Produced.with(longSerde, resultSerde));

		// cart counter
		KStream<Long, GenericRecord> cartStream = builder.stream("test-cart", Consumed.with(longSerde, genericAvroSerde));
		KStream<Long, Result> cartResult = cartStream.map(((key, value) -> KeyValue.pair(key, Result.newBuilder()
				.setUserId(value.get("userId").toString())
				.setAction(ActionType.CART)
				.build())));
		cartResult.foreach((key, value) -> log.info("Cart Stream >> key: {}, value: {}", key, value));
		cartResult.to("test-result1", Produced.with(longSerde, resultSerde));

		// adview counter
		KStream<Long, GenericRecord> adviewStream = builder.stream("test-ad-view1", Consumed.with(longSerde, genericAvroSerde));
		adviewStream.foreach((key, value) -> log.info("Adview Stream - key: {}, value: {}", key, value));


		// define join windows
//		JoinWindows adClickJoinWindows = JoinWindows
//				.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(30))  // 30 seconds tolerance
//				.after(Duration.ofSeconds(30)) // click event must happen within 15 seconds after adview event
//				.before(Duration.ZERO);  // click event must happen after adview event

		KStream<Long, String> joined = clickStream
				.join(
						adviewStream.toTable(),
						(clickValue, adviewValue) -> {
							String clickUserId = clickValue.get("userId").toString();
							String adviewUserId = adviewValue.get("userId").toString();
							long adviewTimestamp = (Long) adviewValue.get("timestamp");
							long clickTimestamp = System.currentTimeMillis();

							// Return null if the click event is not within 30 seconds of the ad view event
							if (clickTimestamp - adviewTimestamp > 30 * 1000 || clickTimestamp < adviewTimestamp) {
								return null;
							}

							return adviewUserId + "/" + clickUserId;
						}
				)
				.filter(
						(key, value) -> {
							if (value == null) {
								return false;
							}

							String[] parts = value.split("/");
							return parts[0].equals(parts[1]);  // only keep events where user id is the same
						}
				);

		KStream<Long, Result> ad_click_stream = joined.map(((key, value) -> KeyValue.pair(key, Result.newBuilder()
				.setUserId(value.split("/")[0])
				.setAction(ActionType.AD_CLICK)
				.setTimestamp(System.currentTimeMillis())
				.build())));
		ad_click_stream.foreach((k, v) -> log.info("ad click >> key: " + k + ", value: " + v));
		ad_click_stream.to("test-result1", Produced.with(longSerde, resultSerde));


		// ad click + payment
		KStream<Long, String> ad_payment_joined = paymentStream
				.join(
						ad_click_stream.toTable(),
						(paymentResult, adclickResult) -> {

							long paymentTimestamp = paymentResult.getTimestamp();
							long adclickTimestamp = adclickResult.getTimestamp();

							// Return null if the click event is not within 180 seconds of the ad view event
							if (paymentTimestamp - adclickTimestamp > 180 * 1000 || paymentTimestamp < adclickTimestamp) {
								return null;
							}
							return paymentResult.getUserId().replace("-", "") + "/" + adclickResult.getUserId().replace("-", "");
						},
						Joined.with(longSerde, resultSerde, resultSerde)
				)
				.filter(
						(key, value) -> {
							if (value == null) return false;

							String[] parts = value.split("/");
							return parts[0].equals(parts[1]);  // only keep events where user id is the same
						}
				);

		KStream<Long, Result> ad_payment_stream = ad_payment_joined.map((key, value) -> KeyValue.pair(key, Result.newBuilder()
						.setUserId(value.split("/")[0])
						.setAction(ActionType.AD_PAYMENT)
						.setTimestamp(System.currentTimeMillis())
						.build()));
		ad_payment_stream.foreach((k, v) -> log.info("ad payment >> key: {}, value: {}", k, v));
		ad_payment_stream.to("test-result1", Produced.with(longSerde, resultSerde));


		Topology topology = builder.build();
		KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
		streams.start();
//		streams.close();
	}

}