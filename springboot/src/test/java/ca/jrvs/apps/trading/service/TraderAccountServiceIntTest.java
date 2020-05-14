package ca.jrvs.apps.trading.service;

import static ca.jrvs.apps.trading.TestUtil.NOT_ID;
import static ca.jrvs.apps.trading.TestUtil.getTraderDavid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.repo.AccountRepository;
import ca.jrvs.apps.trading.repo.TraderRepository;
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
public class TraderAccountServiceIntTest {

  @Autowired
  private TraderAccountService service;
  @Autowired
  private TraderRepository traderRepository;
  @Autowired
  private AccountRepository accountRepository;

  private TraderAccountView savedView1;
  private TraderAccountView savedView2;

  @Before
  public void createTraderAccount() {
    Trader trader = getTraderDavid();
    savedView1 = service.createTraderAccount(trader);
    assertEquals(1, traderRepository.count());
    assertEquals(1, accountRepository.count());
    assertEquals(0, savedView1.getAccount().getAmount(), Double.MIN_VALUE);

    // David opened another account
    savedView2 = service.createTraderAccount(trader);
    assertEquals(1, traderRepository.count());
    assertEquals(2, accountRepository.count());
    assertEquals(0, savedView2.getAccount().getAmount(), Double.MIN_VALUE);

    trader.setEmail("not.email");
    try {
      service.createTraderAccount(trader);
      fail();
    } catch (Exception ignored) {
    }
  }

  @Test
  public void depositAndWithdraw() {
    // test deposit
    final Integer accountId = savedView1.getAccount().getId();
    final Double fund = 1e+5d;
    service.deposit(accountId, fund);

    Optional<Account> accountUpdated = accountRepository.findById(accountId);
    assertTrue(accountUpdated.isPresent());
    assertEquals(fund, accountUpdated.get().getAmount());

    try {
      service.deposit(accountId, -1d);
      fail();
    } catch (Exception ignored) {
    }

    try {
      service.deposit(NOT_ID, fund);
      fail();
    } catch (IllegalArgumentException ignored) {
    }

    // test withdraw
    final Double toWithdraw = 1e+4d;
    service.withdraw(accountId, toWithdraw);

    accountUpdated = accountRepository.findById(accountId);
    assertTrue(accountUpdated.isPresent());
    assertEquals(Double.valueOf(fund - toWithdraw), accountUpdated.get().getAmount());

    try {
      service.withdraw(accountId, 1e+6d);
      fail();
    } catch (IllegalArgumentException ignored) {
    }
  }

  @After
  public void deleteTraderById() {
    Optional<Account> account = accountRepository.findById(savedView1.getAccount().getId());
    assertTrue(account.isPresent());
    Double amountLeft = account.get().getAmount();
    assertTrue(amountLeft > 0);
    try {
      service.deleteTraderById(savedView1.getTrader().getId());
      fail();
    } catch (IllegalArgumentException ignored) {
    }

    service.withdraw(savedView1.getAccount().getId(), amountLeft);
    service.deleteTraderById(savedView1.getTrader().getId());

    // nothing to delete
    try {
      service.deleteTraderById(savedView1.getTrader().getId());
      fail();
    } catch (IllegalArgumentException ignored) {
    }
  }

}