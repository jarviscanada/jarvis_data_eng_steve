package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.GeoCoordinate;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.regex.Pattern;

public class TweetUtil {

  private static final int TWEET_LEN_MAX = 140;
  private static final int TWEET_LEN_MIN = 1;
  private static final double LONGITUDE_MAX = 180d;
  private static final double LONGITUDE_MIN = -180d;
  private static final double LATITUDE_MAX = 90d;
  private static final double LATITUDE_MIN = -90d;

  private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]+$");

  /**
   * Build a Tweet object given text and coordinates information
   */
  public static Tweet buildTweet(String text, double longitude, double latitude) {
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Coordinates coordinate = new Coordinates();
    coordinate.setLatitude(latitude);
    coordinate.setLongitude(longitude);
    tweet.setCoordinates(coordinate);
    return tweet;
  }

  public static void validateId(String id) {
    if (!ID_PATTERN.matcher(id).matches()) {
      throw new IllegalArgumentException("Invalid id: " + id);
    }
  }

  public static void validateTweet(Tweet tweet) {
    String text = tweet.getText();
    if (text == null) {
      throw new IllegalArgumentException("Missing status field");
    }
    validateStatus(text);

    Coordinates coordinates = tweet.getCoordinates();
    if (coordinates != null) {
      validateCoordinates(coordinates);
    }
  }

  private static void validateStatus(String status) {
    if (status.length() < 1) {
      throw new IllegalArgumentException("Length of status should be at least " + TWEET_LEN_MIN);
    }
    if (status.length() > TWEET_LEN_MAX) {
      throw new IllegalArgumentException("Status exceeded " + TWEET_LEN_MAX + " characters");
    }
  }

  private static void validateCoordinates(GeoCoordinate coordinates) {
    double lat = coordinates.getLatitude();
    if (lat < LATITUDE_MIN || lat > LATITUDE_MAX) {
      throw new IllegalArgumentException(
          "Latitude should be in [" + LATITUDE_MIN + " , " + LATITUDE_MAX + "]"
      );
    }
    double lon = coordinates.getLongitude();
    if (lon < LONGITUDE_MIN || lon > LONGITUDE_MAX) {
      throw new IllegalArgumentException(
          "Longitude should be in [" + LONGITUDE_MIN + " , " + LONGITUDE_MAX + "]"
      );
    }
  }

  public static void clearField(Tweet tweet, String fieldName) {
    switch (fieldName) {
      case "created_at":
        tweet.setCreatedAt(null);
        break;
      case "id":
        tweet.setId(null);
        break;
      case "id_str":
        tweet.setIdString(null);
        break;
      case "text":
        tweet.setText(null);
        break;
      case "coordinates":
        tweet.setCoordinates(null);
        break;
      case "entities":
        tweet.setEntities(null);
        break;
      case "retweet_count":
        tweet.setRetweetCount(null);
        break;
      case "favorite_count":
        tweet.setFavoriteCount(null);
        break;
      case "favorited":
        tweet.setFavorited(null);
        break;
      case "retweeted":
        tweet.setRetweeted(null);
        break;
      default:
        throw new IllegalArgumentException("No such a field: " + fieldName);
    }
  }
}
