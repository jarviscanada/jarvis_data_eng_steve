package ca.jrvs.apps.twitter.service;

import static ca.jrvs.apps.twitter.TestUtil.HASHTAG;
import static ca.jrvs.apps.twitter.TestUtil.LATITUDE;
import static ca.jrvs.apps.twitter.TestUtil.LONGITUDE;
import static ca.jrvs.apps.twitter.TestUtil.MENTION;
import static ca.jrvs.apps.twitter.TestUtil.TEXT;
import static ca.jrvs.apps.twitter.TestUtil.checkTweet;
import static ca.jrvs.apps.twitter.TestUtil.getFieldsWithNullAndMistyped;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import ca.jrvs.apps.twitter.TestUtil;
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

public class TwitterServiceIntTest {

  private final Logger logger = LoggerFactory.getLogger(TwitterServiceIntTest.class);
  private TwitterService service;

  @Before
  public void setUp() {
    AccessKey key = new AccessKey();
    key.loadFromEnv();
    logger.info(key.toString());
    HttpHelper helper = new TwitterHttpHelper(key);
    TwitterDao dao = new TwitterDao(helper);
    this.service = new TwitterService(dao);
  }

  @Test
  public void createFindDelete() {
    logger.info("Testing create...");
    List<Tweet> postedTweets = IntStream.range(0, 3)
        .mapToObj(i -> i + MENTION + TEXT + HASHTAG)
        .map(str -> TweetUtil.buildTweet(str, LONGITUDE, LATITUDE))
        .map(service::postTweet)
        .collect(Collectors.toList());
    postedTweets.forEach(TestUtil::checkTweet);

    logger.info("Testing find...");
    String[] fields = getFieldsWithNullAndMistyped();

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
    removedTweets.forEach(TestUtil::checkTweet);

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

}