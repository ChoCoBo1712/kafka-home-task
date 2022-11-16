package com.stratosky.taxiapp;

import com.stratosky.taxiapp.consumer.VehicleDistanceConsumer;
import com.stratosky.taxiapp.controller.VehicleSignalController;
import com.stratosky.taxiapp.entity.VehicleSignal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
class TaxiAppApplicationTests {
  @Container
  private static final KafkaContainer kafkaContainer =
          new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

  @DynamicPropertySource
  private static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
  }

  @Autowired
  private VehicleDistanceConsumer vehicleDistanceConsumer;
  @Autowired
  private VehicleSignalController vehicleSignalController;

  @Test
  public void testLoggingVehicleDistance() throws InterruptedException {
    VehicleSignal vehicleSignal = new VehicleSignal(66D, 100D);
    vehicleSignalController.processVehicleSignals(1L, vehicleSignal);
    Thread.sleep(7000);

    vehicleSignal.setLatitude(70D);
    vehicleSignal.setLongitude(100D);
    vehicleSignalController.processVehicleSignals(1L, vehicleSignal);
    Thread.sleep(7000);

    Assertions.assertEquals(0, vehicleDistanceConsumer.distances.get(0));
    Assertions.assertEquals(4, vehicleDistanceConsumer.distances.get(1));
  }
}
