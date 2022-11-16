package com.stratosky.taxiapp.service;

import com.stratosky.taxiapp.entity.VehicleSignal;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class VehicleDistanceService {
  private static final Logger logger = LoggerFactory.getLogger(VehicleDistanceService.class);
  private final Map<Long, VehicleSignal> vehicleSignalMap = new HashMap<>();
  private final KafkaProducer<Long, Double> vehicleDistanceProducer;

  @Value("${app.kafka.output-topic}")
  private String topic;

  public VehicleDistanceService(KafkaProducer<Long, Double> vehicleDistanceProducer) {
    this.vehicleDistanceProducer = vehicleDistanceProducer;
  }

  public void calculateAndSendDistance(Long id, VehicleSignal vehicleSignal) {
    double distance = calculate(id, vehicleSignal);
    ProducerRecord<Long, Double> producerRecord = new ProducerRecord<>(topic, distance);
    vehicleDistanceProducer.send(producerRecord);
    logger.info("Message sent to output topic");
  }

  private double calculate(Long id, VehicleSignal vehicleSignal) {
    VehicleSignal cashedVehicleSignal = vehicleSignalMap.get(id);
    if (cashedVehicleSignal != null) {
      vehicleSignalMap.put(id, vehicleSignal);
      double latitudeDistance = Math.abs(vehicleSignal.getLatitude() - cashedVehicleSignal.getLatitude());
      double longitudeDistance = Math.abs(vehicleSignal.getLongitude() - cashedVehicleSignal.getLongitude());
      return Math.pow((Math.pow(latitudeDistance, 2) + Math.pow(longitudeDistance, 2)), 0.5);
    }
    return 0;
  }
}
