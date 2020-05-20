package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;

public class PortfolioView {

  private final Account account;

  private final List<Position> positions;

  public PortfolioView(Account account,
      List<Position> positions) {
    this.account = account;
    this.positions = positions;
  }

  public Account getAccount() {
    return account;
  }

  public List<Position> getPositions() {
    return positions;
  }
}
