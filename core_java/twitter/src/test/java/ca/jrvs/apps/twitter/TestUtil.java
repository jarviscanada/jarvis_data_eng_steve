package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtil;

public class TestUtil {

  public static final String HASHTAG = "#test";
  public static final String MENTION = "@someone";
  public static final String TEXT = " Test message from the North Pole.";
  public static final String FAKE_ID = "0123456789";
  public static final String CAUGHT = "Exception Caught: ";
  public static final double LONGITUDE = 135d;
  public static final double LATITUDE = 90d;
  private static final String[] FIELDS_WITH_NULL_AND_MISTYPED;

  static {
    FIELDS_WITH_NULL_AND_MISTYPED = TweetUtil.getAllFields();
    // someone miss-typed "created_at"
    FIELDS_WITH_NULL_AND_MISTYPED[0] = "create_at";
    // someone doesn't like "id"
    FIELDS_WITH_NULL_AND_MISTYPED[1] = null;
  }

  public static String[] getFieldsWithNullAndMistyped() {
    return FIELDS_WITH_NULL_AND_MISTYPED.clone();
  }

  public static void checkTweet(Tweet tweet) {
    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinatesArray().length);
    assertEquals(LONGITUDE, tweet.getCoordinates().getLongitude(), 0.1);
    assertEquals(LATITUDE, tweet.getCoordinates().getLatitude(), 0.1);
    assertTrue(HASHTAG.contains(tweet.getEntities().getHashtags()[0].getText()));
  }
}
