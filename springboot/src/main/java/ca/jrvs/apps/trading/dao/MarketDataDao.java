package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.dao.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * MarketDataDao is responsible for getting Quotes from IEX
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final Pattern TICKER_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
  private static final String IEX_BATCH_PATH =
      "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private final HttpClientConnectionManager connectionManager;

  @Autowired
  public MarketDataDao(MarketDataConfig config,
      HttpClientConnectionManager connectionManager) {
    this.IEX_BATCH_URL = config.getHost() + IEX_BATCH_PATH + config.getToken();
    this.connectionManager = connectionManager;
  }

  private static void validateTicker(String ticker) {
    if (!TICKER_PATTERN.matcher(ticker).matches()) {
      throw new IllegalArgumentException("Invalid ticker: " + ticker);
    }
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException();
  }

  /**
   * Get an IexQuote
   *
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    List<IexQuote> quotes = (List<IexQuote>) findAllById(Collections.singletonList(ticker));
    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      return Optional.of((quotes.get(0)));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes: " + quotes.size());
    }
  }

  @Override
  public boolean existsById(String ticker) {
    String url = String.format(IEX_BATCH_URL, ticker);
    return executeHttpGet(url).isPresent();
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException();
  }

  /**
   * Get multiple quotes
   *
   * @return a list of IexQuote object
   * @throws DataRetrievalFailureException if unable to retrieve quotes due to HTTP failure,
   *                                       unexpected status code, or other problems
   * @throws IllegalArgumentException      if there exists invalid ticker
   */
  @Override
  public Iterable<IexQuote> findAllById(Iterable<String> tickers) {
    for (String t : tickers) {
      validateTicker(t);
    }
    String url = String.format(IEX_BATCH_URL, String.join(",", tickers));
    Optional<String> results = executeHttpGet(url);

    List<IexQuote> quotes = new ArrayList<>();
    if (results.isPresent()) {
      JSONObject quotesJson = new JSONObject(results.get());
      ObjectMapper mapper = new ObjectMapper();
      for (String t : tickers) {
        String quoteJsonStr = quotesJson.getJSONObject(t).getJSONObject("quote").toString();
        IexQuote q;
        try {
          q = mapper.readValue(quoteJsonStr, IexQuote.class);
        } catch (IOException e) {
          throw new DataRetrievalFailureException("Failed to parse JSON to Quote object", e);
        }
        quotes.add(q);
      }
    }
    return quotes;
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException();
  }

  /**
   * Execute a get and return http entity as a string
   *
   * @param url resource URL
   * @return http response body or Optional.empty for 404 response
   * @throws DataRetrievalFailureException if HTTP failed, or status code is unexpected, or there is
   *                                       something wring with the response body
   */
  private Optional<String> executeHttpGet(String url) {
    HttpUriRequest request = new HttpGet(url);
    CloseableHttpClient client = getHttpClient();
    HttpResponse response;
    try {
      response = client.execute(request);
    } catch (IOException e) {
      throw new DataRetrievalFailureException(e.getMessage(), e);
    }
    String bodyString = parseResponseBody(response);
    // check status code
    int status = response.getStatusLine().getStatusCode();
    switch (status) {
      case HttpStatus.SC_OK:
        return Optional.of(bodyString);
      case HttpStatus.SC_NOT_FOUND:
        logger.warn("Required resource not found, Optional.empty will be returned.");
        return Optional.empty();
      default:
        logger.debug(bodyString);
        throw new DataRetrievalFailureException("Unexpected HTTP status: " + status
            + "\nSee https://iexcloud.io/docs/api/#error-codes");
    }
  }

  private String parseResponseBody(HttpResponse response) {
    HttpEntity entity = response.getEntity();
    if (entity == null) {
      throw new DataRetrievalFailureException("Empty response body");
    }
    try {
      return EntityUtils.toString(entity);
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Failed to convert Entity to String", e);
    }
  }

  /**
   * Borrow a HTTP client from the httpClientConnectionManager
   */
  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(connectionManager)
        .setConnectionManagerShared(true)
        .build();
  }
}
