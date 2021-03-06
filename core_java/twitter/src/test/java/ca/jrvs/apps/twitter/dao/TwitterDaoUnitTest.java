package ca.jrvs.apps.twitter.dao;

import static ca.jrvs.apps.twitter.TestUtil.FAKE_ID;
import static ca.jrvs.apps.twitter.example.JsonParserExample.TWEET_JSON_STR;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  private HttpHelper mockHelper;
  @InjectMocks
  private TwitterDao dao;
  private TwitterDao spyDao;

  @Before
  public void setUp() throws IOException {
    // tweet to mock parseResponseBody
    Tweet expectedTweet = JsonParser.toObjectFromJson(TWEET_JSON_STR, Tweet.class);
    // make a spyDao which can fake parseResponseBody return value
    spyDao = Mockito.spy(dao);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any());
  }

  @Test
  public void create() {
    String hashtag = "#test";
    String mention = "@someone";
    String text =
        System.currentTimeMillis() + mention + " Test message from the North Pole." + hashtag;
    double lon = 135;
    double lat = 90;

    // test failed request
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.create(TweetUtil.buildTweet(text, lon, lat));
      fail();
    } catch (RuntimeException ignored) {
    }

    // test happy path
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);

    Tweet input = TweetUtil.buildTweet(text, lon, lat);
    Tweet tweet = spyDao.create(input);

    verify(spyDao).create(input);
    verify(spyDao).parseResponseBody(null);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void findById() {
    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    Tweet tweet = spyDao.findById(FAKE_ID);

    verify(spyDao).findById(FAKE_ID);
    verify(spyDao).parseResponseBody(null);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteById() {
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    Tweet tweet = spyDao.deleteById(FAKE_ID);

    verify(spyDao).deleteById(FAKE_ID);
    verify(spyDao).parseResponseBody(null);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }
}