package com.stratosky.taxiapp.config;

import com.stratosky.taxiapp.entity.VehicleSignal;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

public class ConsumerConfig {
  private final KafkaProperties kafkaProperties;

  public ConsumerConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  @Scope("prototype")
  public KafkaConsumer<Long, VehicleSignal> vehicleSignalsConsumer() {
    return new KafkaConsumer<>(consumerConfig(true));
  }

  @Bean
  public KafkaConsumer<Long, Double> vehicleDistanceConsumer() {
    return new KafkaConsumer<>(consumerConfig(false));
  }

  private Map<String, Object> consumerConfig(boolean vehicleSignalDeserializer) {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
    if (vehicleSignalDeserializer) {
      props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
              "com.stratosky.taxiapp.entity.serialization.VehicleSignalDeserializer");
    }
    return props;
  }
}