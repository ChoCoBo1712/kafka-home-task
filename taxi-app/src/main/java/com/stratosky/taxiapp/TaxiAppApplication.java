package com.stratosky.taxiapp;

import com.stratosky.taxiapp.entity.VehicleSignal;
import com.stratosky.taxiapp.validator.PostValidator;
import com.stratosky.taxiapp.validator.VehicleSignalValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaxiAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaxiAppApplication.class, args);
  }

  @Bean
  public PostValidator<Long, VehicleSignal> vehicleSignalValidator() {
    return new VehicleSignalValidator();
  }
}
