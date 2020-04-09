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

  public double[] getCoordinates() {
    return coordinates;
  }

  public String getType() {
    return type;
  }

}
