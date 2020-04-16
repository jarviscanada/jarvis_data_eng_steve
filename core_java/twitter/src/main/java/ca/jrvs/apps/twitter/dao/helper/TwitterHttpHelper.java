package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper {

  /**
   * Dependencies are specified as private member variables
   */
  private final OAuthConsumer consumer;
  private final HttpClient httpClient;

  /**
   * Constructor setup dependencies using secrets
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret,
      String accessToken, String tokenSecret) {
    this.consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    this.consumer.setTokenWithSecret(accessToken, tokenSecret);
    this.httpClient = HttpClientBuilder.create().build();
  }

  /**
   * Constructor setup dependencies using AccessKey
   *
   * @see AccessKey
   */
  public TwitterHttpHelper(AccessKey key) {
    this.consumer = new CommonsHttpOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
    this.consumer.setTokenWithSecret(key.getAccessToken(), key.getTokenSecret());
    this.httpClient = HttpClientBuilder.create().build();
  }

  /**
   * Default constructor
   */
  public TwitterHttpHelper() {
    AccessKey key = new AccessKey();
    key.loadFromEnv();
    this.consumer = new CommonsHttpOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
    this.consumer.setTokenWithSecret(key.getAccessToken(), key.getTokenSecret());
    this.httpClient = HttpClientBuilder.create().build();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.POST, uri, null);
    } catch (OAuthException | IOException e) {
      throw new RuntimeException("Failed to execute", e);
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.GET, uri, null);
    } catch (OAuthException | IOException e) {
      throw new RuntimeException("Failed to execute", e);
    }
  }

  private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity entity)
      throws OAuthException, IOException {
    HttpUriRequest request;
    switch (method) {
      case GET:
        request = new HttpGet(uri);
        break;
      case POST:
        request = new HttpPost(uri);
        break;
      default:
        throw new IllegalArgumentException("Unsupported HTTP method: " + method);
    }
    this.consumer.sign(request);
    return this.httpClient.execute(request);
  }
}
