package ca.jrvs.apps.trading.repo;

import static ca.jrvs.apps.trading.TestUtil.NOT_ID;
import static ca.jrvs.apps.trading.TestUtil.getQuoteRbc;
import static ca.jrvs.apps.trading.TestUtil.getQuoteShop;
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
public class QuoteRepositoryTest {

  @Autowired
  private QuoteRepository repository;

  private final static Quote savedQuote1 = getQuoteShop();
  private final static Quote savedQuote2 = getQuoteRbc();

  @Before
  public void setUp() {
    repository.save(savedQuote1);
    repository.save(savedQuote2);
  }

  @After
  public void tearDown() {
    repository.deleteAll();
  }

  @Test
  public void find() {
    List<Quote> all = repository.findAll();
    assertEquals(2, all.size());

    Optional<Quote> quote1 = repository.findById(savedQuote1.getId());
    assertTrue(quote1.isPresent());
    assertEquals(savedQuote1, quote1.get());

    Optional<Quote> quote2 = repository.findById(savedQuote2.getId());
    assertTrue(quote2.isPresent());
    assertEquals(savedQuote2, quote2.get());

    Optional<Quote> quote3 = repository.findById(NOT_ID);
    assertTrue(quote3.isEmpty());
  }

  @Test
  public void delete() {
    assertEquals(2, repository.count());
    assertTrue(repository.existsById(savedQuote1.getId()));
    assertTrue(repository.existsById(savedQuote2.getId()));

    repository.deleteById(savedQuote1.getId());
    assertEquals(1, repository.count());
    assertFalse(repository.existsById(savedQuote1.getId()));
    assertTrue(repository.existsById(savedQuote2.getId()));

    repository.delete(savedQuote2);
    assertEquals(0, repository.count());
    assertFalse(repository.existsById(savedQuote2.getId()));

    // do nothing
    repository.delete(savedQuote1);
    repository.delete(savedQuote2);
  }

  @Test
  public void update() {
    List<Quote> all = repository.findAll();
    int newAskSize = all.get(0).getAskSize() + 1;
    all.get(0).setAskSize(newAskSize);
    repository.saveAll(all);

    Optional<Quote> quote1 = repository.findById(all.get(0).getId());
    assertTrue(quote1.isPresent());
    assertEquals(newAskSize, quote1.get().getAskSize().intValue());

    Optional<Quote> quote2 = repository.findById(all.get(1).getId());
    assertTrue(quote2.isPresent());
    assertEquals(savedQuote2, quote2.get());
  }

}