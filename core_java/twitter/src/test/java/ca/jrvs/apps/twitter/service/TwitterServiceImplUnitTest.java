package ca.jrvs.apps.twitter.service;

import static ca.jrvs.apps.twitter.TestUtil.CAUGHT;
import static ca.jrvs.apps.twitter.TestUtil.FAKE_ID;
import static ca.jrvs.apps.twitter.TestUtil.TEXT;
import static ca.jrvs.apps.twitter.TestUtil.getFieldsWithNullAndMistyped;
import static ca.jrvs.apps.twitter.example.JsonParserExample.TWEET_JSON_STR;
import static ca.jrvs.apps.twitter.utils.TweetUtil.LATITUDE_MAX;
import static ca.jrvs.apps.twitter.utils.TweetUtil.LONGITUDE_MIN;
import static ca.jrvs.apps.twitter.utils.TweetUtil.TWEET_LEN_MAX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceImplUnitTest {

  private final Logger logger = LoggerFactory.getLogger(TwitterServiceImplUnitTest.class);
  private Tweet expectedTweet;

  @Mock
  TwitterDao dao;
  @InjectMocks
  TwitterServiceImpl service;

  private static String makeString(int n, char fill) {
    return CharBuffer.allocate(n).toString().replace('\0', fill);
  }

  @Before
  public void setUp() throws IOException {
    expectedTweet = JsonParser.toObjectFromJson(TWEET_JSON_STR, Tweet.class);
  }

  @Test
  public void postTweet() {
    when(dao.create(any(Tweet.class))).thenReturn(expectedTweet);
    Tweet tweet = service.postTweet(TweetUtil.buildTweet(TEXT, LONGITUDE_MIN, LATITUDE_MAX));
    assertEquals(expectedTweet, tweet);

    try {
      service.postTweet(TweetUtil.buildTweet(TEXT, LONGITUDE_MIN, LATITUDE_MAX + 1));
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }

    try {
      service.postTweet(TweetUtil.buildTweet(TEXT, LONGITUDE_MIN - 1, LATITUDE_MAX));
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }

    try {
      service.postTweet(TweetUtil.buildTweet("", LONGITUDE_MIN, LATITUDE_MAX));
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }

    try {
      service.postTweet(TweetUtil.buildTweet(
          makeString(TWEET_LEN_MAX + 1, '.'), LONGITUDE_MIN, LATITUDE_MAX));
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }
  }

  @Test
  public void showTweet() {
    when(dao.findById(anyString())).thenReturn(expectedTweet);

    String[] fields = getFieldsWithNullAndMistyped();
    Tweet tweet = service.showTweet(FAKE_ID, fields);
    TweetUtil.clearField(expectedTweet, "created_at");
    TweetUtil.clearField(expectedTweet, "id");
    assertEquals(expectedTweet, tweet);

    try {
      service.showTweet(makeString(5, '.'), fields);
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }
  }

  @Test
  public void deleteTweets() {
    when(dao.deleteById(anyString())).thenReturn(expectedTweet);
    String[] ids = IntStream.range(1, 5)
        .mapToObj(i -> makeString(i, '1'))
        .toArray(String[]::new);
    List<Tweet> tweets = service.deleteTweets(ids);
    tweets.forEach(t -> assertEquals(expectedTweet, t));

    ids[0] = makeString(5, '.');
    try {
      service.deleteTweets(ids);
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }
  }
}