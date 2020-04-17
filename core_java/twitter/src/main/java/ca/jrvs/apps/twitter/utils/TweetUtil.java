package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.GeoCoordinate;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class TweetUtil {

  public static final int TWEET_LEN_MAX = 140;
  public static final int TWEET_LEN_MIN = 1;
  public static final double LONGITUDE_MAX = 180d;
  public static final double LONGITUDE_MIN = -180d;
  public static final double LATITUDE_MAX = 90d;
  public static final double LATITUDE_MIN = -90d;

  public static final String COORD_SEP = ":";
  public static final String FIELD_SEP = ",";
  public static final String CMD_POST = "post";
  public static final String CMD_SHOW = "show";
  public static final String CMD_DEL = "delete";
  private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]+$");

  private static final String[] ALL_FIELDS;
  private static final HashMap<String, Method> SETTER_TABLE;

  static {
    LinkedList<String> allFields = new LinkedList<>();
    SETTER_TABLE = new HashMap<>();

    for (Method method : Tweet.class.getMethods()) {
      Arrays.stream(Tweet.class.getDeclaredFields())
          .filter(f -> f.isAnnotationPresent(JsonProperty.class))
          .filter(f -> method.getName().toLowerCase()
              .equals("set" + f.getName().toLowerCase()))
          .map(f -> f.getAnnotation(JsonProperty.class).value())
          .forEach(fieldJsonProp -> {
            allFields.add(fieldJsonProp);
            SETTER_TABLE.put(fieldJsonProp, method);
          });
    }
    ALL_FIELDS = allFields.toArray(new String[0]);
  }

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

  /**
   * @throws IllegalArgumentException if input is not a valid id
   */
  public static void validateId(String id) {
    if (!ID_PATTERN.matcher(id).matches()) {
      throw new IllegalArgumentException("Invalid id: " + id);
    }
  }

  /**
   * @throws IllegalArgumentException if input tweet has invalid text or coordinates
   */
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

  /**
   * Set a specific field of the tweet to null
   *
   * @param fieldName Json property of the field to clear
   * @see Tweet
   */
  public static void clearField(Tweet tweet, String fieldName) {
    try {
      SETTER_TABLE.get(fieldName).invoke(tweet, (Object) null);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException("Failed to clear the field: " + fieldName, e);
    }
//    // hard code approach
//    switch (fieldName) {
//      case "created_at":
//        tweet.setCreatedAt(null);
//        break;
//      case "id":
//        tweet.setId(null);
//        break;
//      case "id_str":
//        tweet.setIdString(null);
//        break;
//      case "text":
//        tweet.setText(null);
//        break;
//      case "coordinates":
//        tweet.setCoordinates(null);
//        break;
//      case "entities":
//        tweet.setEntities(null);
//        break;
//      case "retweet_count":
//        tweet.setRetweetCount(null);
//        break;
//      case "favorite_count":
//        tweet.setFavoriteCount(null);
//        break;
//      case "favorited":
//        tweet.setFavorited(null);
//        break;
//      case "retweeted":
//        tweet.setRetweeted(null);
//        break;
//      default:
//        throw new IllegalArgumentException("No such a field: " + fieldName);
//    }
  }

  /**
   * Convert an array of Tweet fields to the string accepted by TwitterController
   *
   * @see ca.jrvs.apps.twitter.controller.TwitterController#showTweet(String[])
   */
  public static String fieldsToString(String[] fields) {
    StringBuilder builder = new StringBuilder();
    for (String f : fields) {
      builder.append(f).append(FIELD_SEP);
    }
    return builder.toString();
  }

  /**
   * @return a deep copy of the input tweet
   */
  public static Tweet cloneTweet(Tweet tweet) {
    try {
      String jsonString = JsonParser.toJson(tweet, false, true);
      return JsonParser.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to clone tweet", e);
    }
  }

  public static String[] getAllFields() {
    return ALL_FIELDS.clone();
  }

}
