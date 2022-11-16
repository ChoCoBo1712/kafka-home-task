package com.stratosky.taxiapp.consumer;

import com.stratosky.taxiapp.entity.VehicleSignal;
import com.stratosky.taxiapp.service.VehicleDistanceService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehicleSignalConsumer {
  private static final Logger logger = LoggerFactory.getLogger(VehicleSignalConsumer.class);

  private final VehicleDistanceService vehicleDistanceService;

  public VehicleSignalConsumer(VehicleDistanceService vehicleDistanceService) {
    this.vehicleDistanceService = vehicleDistanceService;
  }

  @KafkaListener(topics = "${app.kafka.input-topic}")
  public void consume(ConsumerRecord<Long, VehicleSignal> record) {
    logger.info("Received message from input topic");
    vehicleDistanceService.calculateAndSendDistance(record.key(), record.value());
  }
}
