package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TwitterDao constructs Twitter REST API URIs and make HTTP calls using HttpHelper.
 */
public class TwitterDao implements CrdDao<Tweet, String> {

  /* URI constants */
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";

  /* URI symbols */
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private final HttpHelper httpHelper;
  private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);
  private final PercentEscaper escaper = new PercentEscaper("", false);

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet tweet) {
    try {
      HttpResponse response = httpHelper.httpPost(getPostUri(tweet));
      return parseResponseBody(response);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid tweet input", e);
    }
  }

  @Override
  public Tweet findById(String id) {
    try {
      HttpResponse response = httpHelper.httpGet(getShowUri(id));
      return parseResponseBody(response);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid ID input", e);
    }
  }

  @Override
  public Tweet deleteById(String id) {
    try {
      HttpResponse response = httpHelper.httpPost(getDeleteUri(id));
      return parseResponseBody(response);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid ID input", e);
    }
  }

  private URI getPostUri(Tweet tweet) throws URISyntaxException {
    return new URI(API_BASE_URI + POST_PATH + QUERY_SYM
        + getStatusParam(tweet) + AMPERSAND
        + getLatitudeParam(tweet) + AMPERSAND
        + getLongitudeParam(tweet)
    );
  }

  private URI getShowUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + getParam("id", id));
  }

  private URI getDeleteUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + DELETE_PATH + id + ".json");
  }

  private Tweet parseResponseBody(HttpResponse response) {
    // check if empty
    HttpEntity entity = response.getEntity();
    if (entity == null) {
      throw new RuntimeException("Empty response body");
    }
    // covert to string
    String jsonString;
    try {
      jsonString = EntityUtils.toString(entity);
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert Entity to String", e);
    }
    // check status code
    int status = response.getStatusLine().getStatusCode();
    if (status != HttpStatus.SC_OK) {
      logger.info(jsonString);
      throw new RuntimeException("HTTP status: " + status);
    }
    // parse json to tweet
    try {
      return JsonParser.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e) {
      logger.info(jsonString);
      throw new RuntimeException("Failed to covert JSON to Tweet Object", e);
    }
  }

  private String getParam(String key, String value) {
    return key + EQUAL + value;
  }

  private String getStatusParam(Tweet tweet) {
    return getParam("status", escaper.escape(tweet.getText()));
  }

  private String getLongitudeParam(Tweet tweet) {
    return getParam("long", String.valueOf(
        tweet.getCoordinates().getLongitude())
    );
  }

  private String getLatitudeParam(Tweet tweet) {
    return getParam("lat", String.valueOf(
        tweet.getCoordinates().getLatitude())
    );
  }
}
