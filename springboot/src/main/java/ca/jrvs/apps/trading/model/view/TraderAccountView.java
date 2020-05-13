package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import javax.validation.Valid;

public class TraderAccountView {

  private Trader trader;

  public TraderAccountView(@Valid Trader trader, @Valid Account account) {
    this.trader = trader;
    this.account = account;
  }

  private Account account;

  public Trader getTrader() {
    return trader;
  }

  public void setTrader(Trader trader) {
    this.trader = trader;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
