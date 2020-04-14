package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.twitter.dao.helper.AccessKey;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterDaoIntTest {

  private static final String HASHTAG = "#test";
  private static final String MENTION = "@someone";
  private static final String TEXT = " Test message from the North Pole.";
  private static final double LONGITUDE = 135d;
  private static final double LATITUDE = 90d;

  private final Logger logger = LoggerFactory.getLogger(TwitterDaoIntTest.class);
  private TwitterDao dao;

  @Before
  public void setUp() {
    AccessKey key = new AccessKey();
    key.loadFromEnv();
    logger.info(key.toString());
    HttpHelper helper = new TwitterHttpHelper(key);
    this.dao = new TwitterDao(helper);
  }

  @Test
  public void createFindDelete() throws Exception {
    String text =
        System.currentTimeMillis() + MENTION + TEXT + HASHTAG;
    Tweet tweet = TweetUtil.buildTweet(text, LONGITUDE, LATITUDE);
    logger.info(JsonParser.toJson(tweet, true, false));

    logger.info("Testing create...");
    Tweet postedTweet = dao.create(tweet);
    checkTweet(postedTweet);
    assertEquals(text, postedTweet.getText());

    logger.info("Testing find...");
    Tweet lastTweet = dao.findById(postedTweet.getIdString());
    checkTweet(lastTweet);
    assertEquals(postedTweet.getId(), lastTweet.getId());

    logger.info("Testing delete...");
    Tweet removedTweet = dao.deleteById(lastTweet.getIdString());
    checkTweet(lastTweet);
    assertEquals(postedTweet.getId(), removedTweet.getId());

    try {
      dao.findById(removedTweet.getIdString());
      fail();
    } catch (RuntimeException e) {
      logger.info("Content should be deleted, expect 404");
      logger.info(e.getMessage());
    }
  }

  private void checkTweet(Tweet tweet) {
    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinatesArray().length);
    assertEquals(LONGITUDE, tweet.getCoordinates().getLongitude(), 0.1);
    assertEquals(LATITUDE, tweet.getCoordinates().getLatitude(), 0.1);
    assertTrue(HASHTAG.contains(tweet.getEntities().getHashtags()[0].getText()));
  }
}
