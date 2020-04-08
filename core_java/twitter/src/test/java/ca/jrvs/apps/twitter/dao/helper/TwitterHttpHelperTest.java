package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  private TwitterHttpHelper helper;

  @Before
  public void setUp() {
    System.out.println("--@Before method runs before each @Test method");
    this.helper = new TwitterHttpHelper(
        System.getenv("consumerKey"),
        System.getenv("consumerSecret"),
        System.getenv("accessToken"),
        System.getenv("tokenSecret")
    );
  }

  @Test
  public void httpPost() throws Exception {
    HttpResponse response = helper.httpPost(new URI(
        "https://api.twitter.com/1.1/statuses/update.json?status=Message%20de%20HttpHelper"
    ));
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
  }

  @Test
  public void httpGet() throws Exception {
    HttpResponse response = this.helper.httpGet(new URI(
        "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=realDonaldTrump"
    ));
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
  }
}