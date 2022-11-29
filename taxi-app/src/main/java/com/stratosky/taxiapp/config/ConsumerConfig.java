package com.stratosky.taxiapp.config;

import com.stratosky.taxiapp.entity.VehicleSignal;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfig {
  private final KafkaProperties kafkaProperties;

  public ConsumerConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<Long, VehicleSignal> vehicleSignalListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<Long, VehicleSignal> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactoryWithCustomDeserializer());
    return factory;
  }

  private ConsumerFactory<Long, VehicleSignal> consumerFactoryWithCustomDeserializer() {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
    props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "com.stratosky.taxiapp.entity.serialization.VehicleSignalDeserializer");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<Long, Double> vehicleDistanceListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<Long, Double> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  private ConsumerFactory<Long, Double> consumerFactory() {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
    return new DefaultKafkaConsumerFactory<>(props);
  }
}