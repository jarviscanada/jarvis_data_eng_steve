package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.repo.AccountRepository;
import ca.jrvs.apps.trading.repo.PositionRepository;
import ca.jrvs.apps.trading.repo.SecurityOrderRepository;
import ca.jrvs.apps.trading.repo.TraderRepository;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TraderAccountService extends BaseRepoService {

  @Autowired
  public TraderAccountService(TraderRepository trader, AccountRepository account,
      PositionRepository position, SecurityOrderRepository order) {
    this.traderRepository = trader;
    this.accountRepository = account;
    this.positionRepository = position;
    this.orderRepository = order;
  }

  /**
   * Create a new trader and initialize a new account with zero amount
   */
  public TraderAccountView createTrader(@Valid Trader trader) {
    Trader createdTrader = traderRepository.save(trader);
    return createAccountForTrader(createdTrader);
  }

  /**
   * Create a new trader for trader specified by traderId
   *
   * @throws IllegalArgumentException if unable to find trader
   */
  public TraderAccountView createAccountForTrader(@NotNull Integer traderId) {
    Trader trader = findTraderById(traderId);
    return createAccountForTrader(trader);
  }

  private TraderAccountView createAccountForTrader(Trader trader) {
    Account account = accountRepository.save(new Account(trader));
    return new TraderAccountView(trader, account);
  }

  /**
   * Delete all securityOrders, accounts, and trader associated with given traderId. A trader can be
   * deleted iff it's accounts have no open positions and zero cash balance
   *
   * @throws IllegalArgumentException if trader doesn't exist or unable to delete
   */
  public void deleteTraderById(@NotNull Integer traderId) {
    Trader toDelete = findTraderById(traderId);
    accountRepository.findByTrader(toDelete).forEach(this::deleteAccount);
    traderRepository.delete(toDelete);
  }

  /**
   * Delete all securityOrder and the account associated with given accountId. An account can be
   * deleted iff it has no open positions and zero cash balance
   *
   * @throws IllegalArgumentException if account doesn't exist or unable to delete
   */
  public void deleteAccountById(@NotNull Integer accountId) {
    Account toDelete = findAccountById(accountId);
    deleteAccount(toDelete);
  }

  private void deleteAccount(Account account) {
    // check balance
    if (account.getAmount() != 0) {
      throw new IllegalArgumentException(
          account + " has non-zero balance.");
    }
    // check open positions
    List<Position> positions = positionRepository.findByAccountId(account.getId());
    positions.forEach(p -> {
      if (p.getPosition() != 0) {
        throw new IllegalArgumentException(
            p + " has open position.");
      }
    });
    // if all good
    orderRepository.deleteByAccount(account);
    accountRepository.delete(account);
  }

  /**
   * Deposit a fund to an account by traderId
   *
   * @param fund amount to deposit, must be positive
   * @return updated account
   * @throws IllegalArgumentException if unable to locate input account ID
   */
  public Account deposit(@NotNull Integer accountId, @Positive Double fund) {
    Account account = findAccountById(accountId);
    account.setAmount(account.getAmount() + fund);
    account = accountRepository.save(account);
    return account;
  }

  /**
   * Withdraw a fund from an account by traderId
   *
   * @param fund amount to withdraw, must be positive
   * @return updated account
   * @throws IllegalArgumentException if unable to locate input account ID, or fund is insufficient
   */
  public Account withdraw(@NotNull Integer accountId, @Positive Double fund) {
    Account account = findAccountById(accountId);
    if (account.getAmount() - fund < 0) {
      throw new IllegalArgumentException("Insufficient fund");
    }
    account.setAmount(account.getAmount() - fund);
    account = accountRepository.save(account);
    return account;
  }

}
