package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.dao.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarketDataDaoIntTest {

  private MarketDataDao dao;

  @Before
  public void setUp() {
    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    manager.setMaxTotal(50);
    manager.setDefaultMaxPerRoute(50);
    MarketDataConfig config = new MarketDataConfig();
    config.setHost("https://cloud.iexapis.com/v1");
    config.setToken(System.getenv("IEX_PUB_TOKEN"));

    dao = new MarketDataDao(config, manager);
  }

  @Test
  public void findById() {
    String ticker = "AMZN";
    Optional<IexQuote> quote = dao.findById(ticker);
    assertTrue(quote.isPresent());
    assertEquals(ticker, quote.get().getSymbol());
  }

  @Test
  public void findAllById() {
    List<IexQuote> quoteList = (List<IexQuote>) dao.findAllById(Arrays.asList("FB", "AMZN"));
    assertEquals(2, quoteList.size());
    quoteList.forEach(Assert::assertNotNull);
  }
}