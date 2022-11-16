package com.stratosky.taxiapp.entity;

import java.util.Objects;

public class VehicleSignal {
  private Double latitude;
  private Double longitude;

  public VehicleSignal(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VehicleSignal that = (VehicleSignal) o;

    if (!Objects.equals(latitude, that.latitude)) return false;
    return Objects.equals(longitude, that.longitude);
  }

  @Override
  public int hashCode() {
    int result = latitude != null ? latitude.hashCode() : 0;
    result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "VehicleSignal{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
  }
}
