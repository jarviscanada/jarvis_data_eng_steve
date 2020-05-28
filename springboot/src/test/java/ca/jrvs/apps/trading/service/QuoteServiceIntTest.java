package ca.jrvs.apps.trading.service;

import static ca.jrvs.apps.trading.TestUtil.NOT_TICKER;
import static ca.jrvs.apps.trading.TestUtil.getQuoteRbc;
import static ca.jrvs.apps.trading.TestUtil.getQuoteShop;
import static ca.jrvs.apps.trading.TestUtil.getSomeTickers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.repo.QuoteRepository;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService service;

  @Autowired
  private QuoteRepository repository;

  private static Quote savedQuote1;
  private static Quote savedQuote2;

  @Before
  public void setUp() {
    repository.deleteAll();
    savedQuote1 = getQuoteShop();
    repository.save(savedQuote1);
    savedQuote2 = getQuoteRbc();
    repository.save(savedQuote2);
  }

  @Test
  public void findIexQuoteByTicker() {
    try {
      service.findIexQuoteByTicker(NOT_TICKER);
      fail();
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test
  public void updateMarketData() {
    service.updateMarketData();
    List<Quote> quotes = service.findAllQuotes();
    assertEquals(2, quotes.size());
    quotes.forEach(System.out::println);
  }

  @Test
  public void saveQuotes() {
    List<String> tickers = getSomeTickers();
    List<Quote> quotes = service.saveQuotes(tickers);
    assertEquals(tickers.size(), quotes.size());

    HashSet<String> set = new HashSet<>(tickers);
    set.add(savedQuote1.getTicker());
    set.add(savedQuote2.getTicker());
    assertEquals(set.size(), repository.count());
  }

}