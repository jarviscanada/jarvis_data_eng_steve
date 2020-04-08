package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public interface HttpHelper {

  /**
   * Execute a HTTP Post call
   * @return a HTTP response
   */
  HttpResponse httpPost(URI uri);

  /**
   * Execute a HTTP Get call
   * @return a HTTP response
   */
  HttpResponse httpGet(URI uri);
}
