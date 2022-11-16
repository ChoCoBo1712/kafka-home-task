package com.stratosky.taxiapp.entity.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratosky.taxiapp.entity.VehicleSignal;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleSignalSerializer implements Serializer<VehicleSignal> {
  private static final Logger logger = LoggerFactory.getLogger(VehicleSignalSerializer.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(String topic, VehicleSignal data) {
    try {
      if (data == null) {
        logger.error("Received null at serializing");
        return null;
      }
      return objectMapper.writeValueAsBytes(data);
    } catch (JsonProcessingException e) {
      logger.error("Custom serializer exception");
      throw new SerializationException("Error during serializing VehicleSignal to byte[]");
    }
  }
}
