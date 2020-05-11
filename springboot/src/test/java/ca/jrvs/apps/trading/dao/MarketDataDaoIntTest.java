package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    String ticker1 = "FB";
    assertTrue(dao.existsById(ticker1));
    Optional<IexQuote> quote1 = dao.findById(ticker1);
    assertTrue(quote1.isPresent());
    assertEquals(ticker1, quote1.get().getSymbol());

    String ticker2 = "FB2";
    assertFalse(dao.existsById(ticker2));
    Optional<IexQuote> quote2 = dao.findById(ticker2);
    assertTrue(quote2.isEmpty());

    try {
      dao.findById("?");
      fail();
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test
  public void findAllById() {
    List<IexQuote> quoteList = dao.findAllById(Arrays.asList("FB", "AMZN"));
    assertEquals(2, quoteList.size());
    quoteList.forEach(Assert::assertNotNull);
  }
}