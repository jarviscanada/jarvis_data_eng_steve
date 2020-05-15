package ca.jrvs.apps.trading.repo;

import static ca.jrvs.apps.trading.TestUtil.NOT_ID;
import static ca.jrvs.apps.trading.TestUtil.getQuoteRbc;
import static ca.jrvs.apps.trading.TestUtil.getQuoteShop;
import static ca.jrvs.apps.trading.TestUtil.getTraderDavid;
import static ca.jrvs.apps.trading.TestUtil.getTraderYasuo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SecurityOrderRepositoryTest extends
    BaseRepositoryTest<SecurityOrderRepository, SecurityOrder, Integer> {

  @Autowired
  private QuoteRepository quoteRepository;
  @Autowired
  private TraderRepository traderRepository;
  @Autowired
  private AccountRepository accountRepository;

  Quote savedQuote1;
  Quote savedQuote2;
  Account savedAccount1;
  Account savedAccount2;

  @Before
  @Override
  public void setUp() {
    savedQuote1 = quoteRepository.save(getQuoteShop());
    savedQuote2 = quoteRepository.save(getQuoteRbc());

    Trader trader1 = traderRepository.save(getTraderDavid());
    Trader trader2 = traderRepository.save(getTraderYasuo());

    savedAccount1 = accountRepository.save(new Account(trader1));
    savedAccount2 = accountRepository.save(new Account(trader2));

    super.setUp();
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
    quoteRepository.deleteAll();
    accountRepository.deleteAll();
    traderRepository.deleteAll();
  }

  @Override
  Object modifyOneField(SecurityOrder securityOrder) {
    Integer newSize = securityOrder.getSize() + 1;
    securityOrder.setSize(newSize);
    return newSize;
  }

  @Override
  Object getOneField(SecurityOrder securityOrder) {
    return securityOrder.getSize();
  }

  @Override
  SecurityOrder getTestEntity1() {
    return new SecurityOrder(
        "FILLED", 100, 100d, "", savedQuote1, savedAccount1
    );
  }

  @Override
  SecurityOrder getTestEntity2() {
    return new SecurityOrder(
        "FILLED", 10, 1000d, "", savedQuote2, savedAccount2
    );
  }

  @Override
  Integer getNonExistId() {
    return NOT_ID;
  }

  @Test
  public void deleteByAccount() {
    long count = repo.count();
    repo.deleteByAccount(savedAccount1);
    assertEquals(count - 1, repo.count());

    assertTrue(repo.findByAccount(savedAccount1).isEmpty());
    assertFalse(repo.findByQuote(savedQuote2).isEmpty());
  }
}