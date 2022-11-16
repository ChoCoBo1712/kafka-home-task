package com.stratosky.taxiapp.config;

import com.stratosky.taxiapp.entity.VehicleSignal;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {
  private final KafkaProperties kafkaProperties;
  @Value("${app.kafka.producer.enable.idempotence}")
  private boolean enableIdempotence;
  @Value("${app.kafka.producer.max.in.flight.requests.per.connection}")
  private int maxInFlightRequestsPerConnection;

  public ProducerConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public KafkaProducer<Long, VehicleSignal> vehicleSignalProducer() {
    return new KafkaProducer<>(producerConfig(true));
  }

  @Bean
  @Scope("prototype")
  public KafkaProducer<Long, Double> vehicleDistanceProducer() {
    return new KafkaProducer<>(producerConfig(false));
  }

  private Map<String, Object> producerConfig(boolean vehicleSignalSerializer) {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
    props.put(org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
    props.put(org.apache.kafka.clients.producer.ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,
            Integer.toString(maxInFlightRequestsPerConnection));
    if (vehicleSignalSerializer) {
      props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
              "com.stratosky.taxiapp.entity.serialization.VehicleSignalSerializer");
    }
    return props;
  }
}
