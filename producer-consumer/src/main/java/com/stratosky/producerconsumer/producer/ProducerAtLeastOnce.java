package com.stratosky.producerconsumer.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerAtLeastOnce {

  private final KafkaProducer<String, Object> kafkaProducer;
  @Value("${app.kafka.topic}")
  private String topic;

  public ProducerAtLeastOnce(KafkaProducer<String, Object> kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  public void send(String message) {
    ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, message);
    kafkaProducer.send(producerRecord);
  }

  public void close() {
    kafkaProducer.close();
  }
}
