package com.stratosky.taxiapp.controller;

import com.stratosky.taxiapp.entity.VehicleSignal;
import com.stratosky.taxiapp.validator.PostValidator;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/vehicles")
public class VehicleSignalController {
  private static final Logger logger = LoggerFactory.getLogger(VehicleSignalController.class);
  private final KafkaProducer<Long, VehicleSignal> vehicleSignalProducer;
  private final PostValidator<Long, VehicleSignal> vehicleSignalValidator;

  @Value("${app.kafka.input-topic}")
  private String topic;

  public VehicleSignalController(
          KafkaProducer<Long, VehicleSignal> vehicleSignalProducer,
          PostValidator<Long, VehicleSignal> vehicleSignalValidator
  ) {
    this.vehicleSignalProducer = vehicleSignalProducer;
    this.vehicleSignalValidator = vehicleSignalValidator;
  }

  @PostMapping("/{id}")
  public ResponseEntity<String> processVehicleSignals(@PathVariable Long id, @RequestBody VehicleSignal vehicleSignal) {
    if (!vehicleSignalValidator.validate(id, vehicleSignal)) {
      logger.error("Invalid POST request");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data\n");
    }

    ProducerRecord<Long, VehicleSignal> producerRecord = new ProducerRecord<>(topic, vehicleSignal);
    vehicleSignalProducer.send(producerRecord);
    logger.info("Message sent to input topic");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Message accepted\n");
  }
}
