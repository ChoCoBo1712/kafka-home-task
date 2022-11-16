package com.stratosky.taxiapp.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleDistanceConsumer {

  private static final Logger logger = LoggerFactory.getLogger(VehicleDistanceConsumer.class);
  public final List<Double> distances = new ArrayList<>();

  @KafkaListener(topics = "${app.kafka.output-topic}")
  public void consume(ConsumerRecord<Long, Double> record) {
    distances.add(record.value());
    logger.info("Vehicle with Key: " + record.key() + " drove a distance: " + record.value());
  }
}
