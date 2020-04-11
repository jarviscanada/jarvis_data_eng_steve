package ca.jrvs.apps.twitter.model;

public interface GeoCoordinate {

  /**
   * Longitude specifies the east–west position of a point on the Earth's surface
   */
  double getLongitude();

  /**
   * Latitude specifies the north–south position of a point on the Earth's surface.
   */
  double getLatitude();

  void setLongitude(double x);

  void setLatitude(double x);
}
