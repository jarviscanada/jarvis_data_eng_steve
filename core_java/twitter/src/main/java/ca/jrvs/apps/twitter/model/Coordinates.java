package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates implements GeoCoordinate {

  private static final int LONGITUDE_INDEX = 0;
  private static final int LATITUDE_INDEX = 1;

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
  private double[] coordinatesArray = new double[2];

  public double[] getCoordinatesArray() {
    return coordinatesArray;
  }

  public String getType() {
    return type;
  }

  @Override
  public double getLongitude() {
    return coordinatesArray[LONGITUDE_INDEX];
  }

  @Override
  public double getLatitude() {
    return coordinatesArray[LATITUDE_INDEX];
  }

  @Override
  public void setLongitude(double x) {
    coordinatesArray[LONGITUDE_INDEX] = x;
  }

  @Override
  public void setLatitude(double x) {
    coordinatesArray[LATITUDE_INDEX] = x;
  }
}
