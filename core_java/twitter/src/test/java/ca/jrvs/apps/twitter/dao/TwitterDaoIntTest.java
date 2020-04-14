package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.AccessKey;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterDaoIntTest {

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
    String hashtag = "#test";
    String mention = "@someone";
    String text =
        System.currentTimeMillis() + mention + " Test message from the North Pole." + hashtag;
    double lon = 135;
    double lat = 90;
    Tweet tweet = TweetUtil.buildTweet(text, lon, lat);
    logger.info(JsonParser.toJson(tweet, true, false));

    logger.info("Testing create...");
    Tweet postedTweet = dao.create(tweet);

    Assert.assertEquals(text, postedTweet.getText());
    Assert.assertNotNull(postedTweet.getCoordinates());
    Assert.assertEquals(2, postedTweet.getCoordinates().getCoordinatesArray().length);
    Assert.assertEquals(lon, postedTweet.getCoordinates().getLongitude(), 0.1);
    Assert.assertEquals(lat, postedTweet.getCoordinates().getLatitude(), 0.1);
    Assert.assertTrue(hashtag.contains(postedTweet.getEntities().getHashtags()[0].getText()));

    logger.info("Testing find...");
    Tweet lastTweet = dao.findById(postedTweet.getIdString());

    Assert.assertEquals(postedTweet.getId(), lastTweet.getId());
    Assert.assertEquals(text, lastTweet.getText());
    Assert.assertNotNull(lastTweet.getCoordinates());
    Assert.assertEquals(lon, lastTweet.getCoordinates().getLongitude(), 0.1);
    Assert.assertEquals(lat, lastTweet.getCoordinates().getLatitude(), 0.1);
    Assert.assertTrue(hashtag.contains(lastTweet.getEntities().getHashtags()[0].getText()));

    logger.info("Testing delete...");
    Tweet removedTweet = dao.deleteById(lastTweet.getIdString());

    Assert.assertEquals(postedTweet.getId(), removedTweet.getId());
    Assert.assertEquals(text, removedTweet.getText());
    Assert.assertNotNull(removedTweet.getCoordinates());
    Assert.assertEquals(lon, removedTweet.getCoordinates().getLongitude(), 0.1);
    Assert.assertEquals(lat, removedTweet.getCoordinates().getLatitude(), 0.1);
    Assert.assertTrue(hashtag.contains(removedTweet.getEntities().getHashtags()[0].getText()));

    Tweet noTweet = null;
    try {
      noTweet = dao.findById(removedTweet.getIdString());
    } catch (RuntimeException e) {
      logger.info("Content should be deleted, expect 404");
      logger.info(e.getMessage());
    }
    Assert.assertNull(noTweet);
  }
}
