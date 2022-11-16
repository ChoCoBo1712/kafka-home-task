package com.stratosky.taxiapp.validator;

import com.stratosky.taxiapp.entity.VehicleSignal;

public class VehicleSignalValidator implements PostValidator<Long, VehicleSignal> {
  @Override
  public boolean validate(Long id, VehicleSignal vehicleSignal) {
    boolean valid = validateId(id);
    valid &= validateLatitude(vehicleSignal.getLatitude());
    valid &= validateLongitude(vehicleSignal.getLongitude());
    return valid;
  }

  private boolean validateId(Long id) {
    return id != null && id >= 0;
  }

  private boolean validateLatitude(Double latitude) {
    return latitude != null && latitude >= -90 && latitude <= 90;
  }

  private boolean validateLongitude(Double longitude) {
    return longitude != null && longitude >= -180 && longitude <= 180;
  }
}
