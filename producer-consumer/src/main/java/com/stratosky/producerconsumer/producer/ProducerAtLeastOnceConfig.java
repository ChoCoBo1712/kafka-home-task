package com.stratosky.producerconsumer.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerAtLeastOnceConfig {
  private final KafkaProperties kafkaProperties;
  @Value("${app.kafka.producer.enable.idempotence}")
  private boolean enableIdempotence;
  @Value("${app.kafka.producer.max.in.flight.requests.per.connection}")
  private int maxInFlightRequestsPerConnection;

  public ProducerAtLeastOnceConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public KafkaProducer<String, Object> kafkaProducer() {
    return new KafkaProducer<>(producerConfig());
  }

  private Map<String, Object> producerConfig() {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
    props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, Integer.toString(maxInFlightRequestsPerConnection));
    return props;
  }
}
