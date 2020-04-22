package ca.jrvs.apps.twitter.controller;

import static ca.jrvs.apps.twitter.TestUtil.CAUGHT;
import static ca.jrvs.apps.twitter.TestUtil.FAKE_ID;
import static ca.jrvs.apps.twitter.TestUtil.LATITUDE;
import static ca.jrvs.apps.twitter.TestUtil.LONGITUDE;
import static ca.jrvs.apps.twitter.TestUtil.TEXT;
import static ca.jrvs.apps.twitter.example.JsonParserExample.TWEET_JSON_STR;
import static ca.jrvs.apps.twitter.utils.TweetUtil.CMD_DEL;
import static ca.jrvs.apps.twitter.utils.TweetUtil.CMD_POST;
import static ca.jrvs.apps.twitter.utils.TweetUtil.CMD_SHOW;
import static ca.jrvs.apps.twitter.utils.TweetUtil.COORD_SEP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.utils.JsonParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  private final Logger logger = LoggerFactory.getLogger(TwitterControllerUnitTest.class);

  @Mock
  Service service;
  @InjectMocks
  TwitterController controller;
  private Tweet expectedTweet;

  @Before
  public void setUp() throws Exception {
    expectedTweet = JsonParser.toObjectFromJson(TWEET_JSON_STR, Tweet.class);
  }

  @Test
  public void postTweet() {
    when(service.postTweet(any(Tweet.class))).thenReturn(expectedTweet);
    Tweet tweet = controller
        .postTweet(new String[]{CMD_POST, TEXT, LATITUDE + COORD_SEP + LONGITUDE});
    assertEquals(expectedTweet, tweet);

    try {
      controller.postTweet(new String[]{CMD_POST, TEXT, LATITUDE + COORD_SEP});
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }

    try {
      controller.postTweet(new String[]{CMD_POST, TEXT});
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }
  }

  @Test
  public void showTweet() {
    when(service.showTweet(anyString(), any())).thenReturn(expectedTweet);

    String fields = "id,text,retweet_count";
    Tweet tweet = controller.showTweet(new String[]{CMD_SHOW, FAKE_ID, fields});
    assertEquals(expectedTweet, tweet);

    tweet = controller.showTweet(new String[]{CMD_SHOW, FAKE_ID});
    assertEquals(expectedTweet, tweet);

    try {
      controller.showTweet(new String[]{CMD_SHOW, FAKE_ID, fields, TEXT});
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }
  }

  @Test
  public void deleteTweet() {
    when(service.deleteTweets(any())).thenReturn(new ArrayList<>());
    List<Tweet> emptyList = service.deleteTweets(new String[]{CMD_DEL, FAKE_ID, FAKE_ID});
    assertNotNull(emptyList);
    assertTrue(emptyList.isEmpty());

    try {
      controller.deleteTweet(new String[]{CMD_DEL});
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(CAUGHT + e.getMessage());
    }
  }
}