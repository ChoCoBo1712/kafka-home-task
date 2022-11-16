package com.stratosky.taxiapp.entity.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratosky.taxiapp.entity.VehicleSignal;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class VehicleSignalDeserializer implements Deserializer<VehicleSignal> {
  private static final Logger logger = LoggerFactory.getLogger(VehicleSignalDeserializer.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public VehicleSignal deserialize(String topic, byte[] data) {
    try {
      if (data == null) {
        logger.error("Received null at deserializing");
        return null;
      }
      return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), VehicleSignal.class);
    } catch (JsonProcessingException e) {
      logger.error("Custom deserializer exception");
      throw new SerializationException("Error during deserializing byte[] to VehicleSignal");
    }
  }
}
