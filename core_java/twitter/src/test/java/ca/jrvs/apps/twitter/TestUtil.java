package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.model.Tweet;

public class TestUtil {

  public static final String HASHTAG = "#test";
  public static final String MENTION = "@someone";
  public static final String TEXT = " Test message from the North Pole.";
  public static final String FAKE_ID = "0123456789";
  public static final String CAUGHT = "Exception Caught: ";
  public static final double LONGITUDE = 135d;
  public static final double LATITUDE = 90d;

  /**
   * Find and replace some value in a string array
   */
  public static void replaceField(String[] fields, String old, String replacement) {
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].equals(old)) {
        fields[i] = replacement;
        return;
      }
    }
    throw new IllegalArgumentException("Input array doesn't have field: " + old);
  }

  /**
   * Check if the input tweet has the preset longitude, latitude, and hashtag
   */
  public static void checkTweet(Tweet tweet) {
    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinatesArray().length);
    assertEquals(LONGITUDE, tweet.getCoordinates().getLongitude(), 0.1);
    assertEquals(LATITUDE, tweet.getCoordinates().getLatitude(), 0.1);
    assertTrue(HASHTAG.contains(tweet.getEntities().getHashtags()[0].getText()));
  }
}
