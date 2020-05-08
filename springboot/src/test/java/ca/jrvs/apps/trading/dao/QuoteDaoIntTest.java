package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import org.junit.After;
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
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao dao;

  private final static Quote savedQuote1;
  private final static Quote savedQuote2;
  private final static String NOT_ID = "FOO";

  static {
    savedQuote1 = new Quote();
    savedQuote1.setAskPrice(1000d);
    savedQuote1.setAskSize(10);
    savedQuote1.setBidPrice(1000.2d);
    savedQuote1.setBidSize(10);
    savedQuote1.setId("SHOP");
    savedQuote1.setLastPrice(1000.1d);

    savedQuote2 = new Quote();
    savedQuote2.setAskPrice(100d);
    savedQuote2.setAskSize(100);
    savedQuote2.setBidPrice(100.2d);
    savedQuote2.setBidSize(100);
    savedQuote2.setId("RY");
    savedQuote2.setLastPrice(100.1d);
  }

  @Before
  public void insertTwo() {
    dao.save(savedQuote1);
    dao.save(savedQuote2);
  }

  @After
  public void clear() {
    dao.deleteAll();
  }

  @Test
  public void find() {
    List<Quote> all = dao.findAll();
    assertEquals(2, all.size());

    Optional<Quote> quote1 = dao.findById(savedQuote1.getID());
    assertTrue(quote1.isPresent());
    assertEquals(savedQuote1, quote1.get());

    Optional<Quote> quote2 = dao.findById(savedQuote2.getID());
    assertTrue(quote2.isPresent());
    assertEquals(savedQuote2, quote2.get());

    Optional<Quote> quote3 = dao.findById(NOT_ID);
    assertTrue(quote3.isEmpty());
  }

  @Test
  public void delete() {
    assertEquals(2, dao.count());
    assertTrue(dao.existsById(savedQuote1.getID()));
    assertTrue(dao.existsById(savedQuote2.getID()));

    dao.deleteById(savedQuote1.getID());
    assertEquals(1, dao.count());
    assertFalse(dao.existsById(savedQuote1.getID()));
    assertTrue(dao.existsById(savedQuote2.getID()));

    dao.delete(savedQuote2);
    assertEquals(0, dao.count());
    assertFalse(dao.existsById(savedQuote2.getID()));

    // do nothing
    dao.delete(savedQuote1);
    dao.delete(savedQuote2);
  }

  @Test
  public void update() {
    List<Quote> all = dao.findAll();
    all.get(0).setAskSize(20);
    dao.saveAll(all);

    Optional<Quote> quote1 = dao.findById(all.get(0).getID());
    assertTrue(quote1.isPresent());
    assertEquals(20, quote1.get().getAskSize().intValue());

    Optional<Quote> quote2 = dao.findById(all.get(1).getID());
    assertTrue(quote2.isPresent());
    assertEquals(savedQuote2, quote2.get());
  }
}