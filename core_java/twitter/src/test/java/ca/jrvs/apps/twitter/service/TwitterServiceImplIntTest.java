package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.AccessKey;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterServiceImplIntTest {

  private static final String HASHTAG = "#test";
  private static final String MENTION = "@someone";
  private static final String TEXT = " Test message from the North Pole.";
  private static final double LONGITUDE = 135d;
  private static final double LATITUDE = 90d;

  private final Logger logger = LoggerFactory.getLogger(TwitterServiceImplIntTest.class);
  private TwitterServiceImpl service;

  @Before
  public void setUp() {
    AccessKey key = new AccessKey();
    key.loadFromEnv();
    logger.info(key.toString());
    HttpHelper helper = new TwitterHttpHelper(key);
    TwitterDao dao = new TwitterDao(helper);
    this.service = new TwitterServiceImpl(dao);
  }

  @Test
  public void createFindDelete() {
    logger.info("Testing create...");
    List<Tweet> postedTweets = IntStream.range(0, 3)
        .mapToObj(i -> i + MENTION + TEXT + HASHTAG)
        .map(str -> TweetUtil.buildTweet(str, LONGITUDE, LATITUDE))
        .map(service::postTweet)
        .collect(Collectors.toList());
    postedTweets.forEach(this::checkTweet);

    logger.info("Testing find...");
    // fields we want to show
    String[] fields = TwitterServiceImpl.getAllFields();
    // someone miss-typed "created_at"
    fields[0] = "create_at";
    // someone doesn't like "id"
    fields[1] = null;

    List<Tweet> lastTweets = postedTweets.stream()
        .map(Tweet::getIdString)
        .map(id -> service.showTweet(id, fields))
        .collect(Collectors.toList());
    lastTweets.forEach(t -> {
      checkTweet(t);
      assertNull(t.getCreateAt());
      assertNull(t.getId());
    });

    logger.info("Testing delete...");
    String[] ids = lastTweets.stream()
        .map(Tweet::getIdString)
        .toArray(String[]::new);
    List<Tweet> removedTweets = service.deleteTweets(ids);
    removedTweets.forEach(this::checkTweet);

    // Do they still exist?
    for (String id : ids) {
      try {
        service.showTweet(id, null);
        fail();
      } catch (RuntimeException e) {
        logger.info(id + " should be deleted, expect 404");
        logger.info(e.getMessage());
      }
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