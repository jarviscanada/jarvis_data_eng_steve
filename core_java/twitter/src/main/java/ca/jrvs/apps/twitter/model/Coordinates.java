package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {

  /**
   * The type of data encoded in the coordinates property. This will be “Point” for Tweet
   * coordinates fields.
   */
  @JsonProperty("type")
  private final String type = "Point";
  /**
   * The longitude and latitude of the Tweet’s location, as a collection in the form {longitude,
   * latitude}.
   */
  @JsonProperty("coordinates")
  private double[] coordinates;

  public Coordinates(double[] coordinates) {
    if (coordinates.length != 2) {
      throw new IllegalArgumentException(
          "Coordinates array must be of length 2: {longitude, latitude}");
    }
    if (coordinates[0] < -180 || coordinates[0] > 180) {
      throw new IllegalArgumentException(
          "Invalid longitude value, should be in [-180, 180]");
    }
    if (coordinates[1] < -90 || coordinates[1] > 90) {
      throw new IllegalArgumentException(
          "Invalid latitude value, should be in [-90, 90]");
    }
    this.coordinates = coordinates;
  }

  public double[] getCoordinates() {
    return coordinates;
  }

  public String getType() {
    return type;
  }

}
