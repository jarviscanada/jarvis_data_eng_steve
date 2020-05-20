package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.List;

public class TraderAccountsView {

  private final Trader trader;
  private final List<Account> accounts;

  public TraderAccountsView(Trader trader, List<Account> accounts) {
    this.trader = trader;
    this.accounts = accounts;
  }

  public Trader getTrader() {
    return trader;
  }

  public List<Account> getAccounts() {
    return accounts;
  }
}
