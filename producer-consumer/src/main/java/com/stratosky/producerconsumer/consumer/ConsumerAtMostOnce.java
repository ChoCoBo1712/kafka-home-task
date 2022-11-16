package com.stratosky.producerconsumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsumerAtMostOnce {
  public final List<String> messages = new ArrayList<>();

  @KafkaListener(topics = "${app.kafka.topic}")
  public void consume(@Payload String value) {
    messages.add(value);
  }
}
