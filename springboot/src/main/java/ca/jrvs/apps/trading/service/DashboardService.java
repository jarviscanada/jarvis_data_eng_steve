package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.model.view.TraderAccountsView;
import ca.jrvs.apps.trading.repo.AccountRepository;
import ca.jrvs.apps.trading.repo.PositionRepository;
import ca.jrvs.apps.trading.repo.QuoteRepository;
import ca.jrvs.apps.trading.repo.TraderRepository;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class DashboardService extends BaseRepoService {

  @Autowired
  public DashboardService(TraderRepository trader, PositionRepository position,
      AccountRepository account, QuoteRepository quote) {
    this.traderRepository = trader;
    this.positionRepository = position;
    this.accountRepository = account;
    this.quoteRepository = quote;
  }

  /**
   * Create and return a traderAccountView by account Id
   *
   * @throws IllegalArgumentException if unable to find account by Id
   */
  public TraderAccountView viewTraderAccountByAccountId(@NotNull Integer accountId) {
    Account account = findAccountById(accountId);
    return new TraderAccountView(account.getTrader(), account);
  }

  /**
   * Create and return a List of traderAccountView by trader Id
   *
   * @throws IllegalArgumentException if unable to find trader by Id
   */
  public TraderAccountsView viewTraderAccountByTraderId(@NotNull Integer traderId) {
    Trader trader = findTraderById(traderId);
    return new TraderAccountsView(trader, accountRepository.findByTrader(trader));
  }

  /**
   * Create and return portfolioView by account Id
   *
   * @throws IllegalArgumentException if unable to find account by Id
   */
  public PortfolioView viewProfileByAccountId(@NotNull Integer accountId) {
    return new PortfolioView(
        findAccountById(accountId),
        positionRepository.findByAccountId(accountId)
    );
  }
}
