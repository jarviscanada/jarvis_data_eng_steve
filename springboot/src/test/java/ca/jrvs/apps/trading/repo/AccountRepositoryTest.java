package ca.jrvs.apps.trading.repo;

import static ca.jrvs.apps.trading.TestUtil.NOT_ID;
import static ca.jrvs.apps.trading.TestUtil.getTraderDavid;
import static ca.jrvs.apps.trading.TestUtil.getTraderYasuo;
import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRepositoryTest extends BaseRepositoryTest<AccountRepository, Account, Integer> {

  @Autowired
  private TraderRepository traderRepository;

  private Trader savedTrader1;
  private Trader savedTrader2;

  @Before
  @Override
  public void setUp() {
    savedTrader1 = traderRepository.save(getTraderDavid());
    savedTrader2 = traderRepository.save(getTraderYasuo());
    super.setUp();
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
    traderRepository.deleteAll();
  }

  @Override
  Object modifyOneField(Account account) {
    Double newAmount = Double.NaN;
    account.setAmount(newAmount);
    return newAmount;
  }

  @Override
  Object getOneField(Account account) {
    return account.getAmount();
  }

  @Override
  Account getTestEntity1() {
    return new Account(savedTrader1);
  }

  @Override
  Account getTestEntity2() {
    Account account = new Account(savedTrader2);
    account.setAmount(1e+5d);
    return account;
  }

  @Override
  Integer getNonExistId() {
    return NOT_ID;
  }

  @Test
  public void findByTraderId() {
    // trader1 opened another account
    repo.save(getTestEntity1());
    assertEquals(3, repo.count());
    List<Account> accounts = repo.findByTrader(savedTrader1);
    assertEquals(2, accounts.size());
  }
}