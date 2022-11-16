package com.stratosky.taxiapp.config;

import com.stratosky.taxiapp.service.VehicleDistanceService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  private final KafkaProducer<Long, Double> vehicleDistanceProducer;

  public ServiceConfig(KafkaProducer<Long, Double> vehicleDistanceProducer) {
    this.vehicleDistanceProducer = vehicleDistanceProducer;
  }

  @Bean
  public VehicleDistanceService vehicleDistanceService() {
    return new VehicleDistanceService(vehicleDistanceProducer);
  }
}
