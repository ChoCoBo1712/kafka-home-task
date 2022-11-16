package com.stratosky.producerconsumer.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerAtMostOnceConfig {
  private final KafkaProperties kafkaProperties;

  public ConsumerAtMostOnceConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public Map<String, Object> consumerConfig() {
    return new HashMap<>(kafkaProperties.buildConsumerProperties());
  }

  @Bean
  public KafkaConsumer<String, Object> kafkaConsumer() {
    return new KafkaConsumer<>(consumerConfig());
  }
}
