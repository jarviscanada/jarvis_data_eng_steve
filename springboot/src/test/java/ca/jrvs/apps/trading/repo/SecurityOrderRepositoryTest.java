package ca.jrvs.apps.trading.repo;

import static ca.jrvs.apps.trading.TestUtil.NOT_ID;
import static ca.jrvs.apps.trading.TestUtil.getQuoteRbc;
import static ca.jrvs.apps.trading.TestUtil.getQuoteShop;
import static ca.jrvs.apps.trading.TestUtil.getTraderDavid;
import static ca.jrvs.apps.trading.TestUtil.getTraderYasuo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityOrderRepositoryTest extends
    BaseRepositoryTest<SecurityOrderRepository, SecurityOrder, Integer> {

  @Autowired
  private QuoteRepository quoteRepository;
  @Autowired
  private TraderRepository traderRepository;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private PositionRepository positionRepository;

  private Quote savedQuote1;
  private Quote savedQuote2;
  private Account savedAccount1;
  private Account savedAccount2;

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
  public void testPositionRepo() {
    assertEquals(2, positionRepository.count());

    Optional<Position> position1 = positionRepository.findById(savedAccount1.getId());
    assertTrue(position1.isPresent());
    assertEquals(savedQuote1.getTicker(), position1.get().getTicker());
    assertEquals(savedEntity1.getSize(), position1.get().getPosition());

    Optional<Position> position2 = positionRepository.findById(savedAccount2.getId());
    assertTrue(position2.isPresent());
    assertEquals(savedQuote2.getTicker(), position2.get().getTicker());
    assertEquals(savedEntity2.getSize(), position2.get().getPosition());
  }
}