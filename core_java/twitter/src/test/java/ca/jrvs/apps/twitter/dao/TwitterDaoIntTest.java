package ca.jrvs.apps.twitter.dao;

import static ca.jrvs.apps.twitter.TestUtil.HASHTAG;
import static ca.jrvs.apps.twitter.TestUtil.LATITUDE;
import static ca.jrvs.apps.twitter.TestUtil.LONGITUDE;
import static ca.jrvs.apps.twitter.TestUtil.MENTION;
import static ca.jrvs.apps.twitter.TestUtil.TEXT;
import static ca.jrvs.apps.twitter.TestUtil.checkTweet;
import static org.junit.Assert.assertEquals;
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

}
