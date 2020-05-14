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
public class TraderAccountService {

  private final TraderRepository traderRepository;
  private final AccountRepository accountRepository;
  private final PositionRepository positionRepository;
  private final SecurityOrderRepository orderRepository;

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
  public TraderAccountView createTraderAccount(@Valid Trader trader) {
    final Trader createdTrader = traderRepository.save(trader);
    Account traderAccount = new Account(createdTrader);
    traderAccount = accountRepository.save(traderAccount);
    return new TraderAccountView(createdTrader, traderAccount);
  }

  /**
   * A trader can be deleted iff it has no open positions and zero cash balance
   *
   * @throws IllegalArgumentException if trader doesn't exist or unable to delete
   */
  public void deleteTraderById(@NotNull Integer traderId) {
    final Trader toDelete = traderRepository.findById(traderId).orElseThrow(
        () -> new IllegalArgumentException("Trader ID not found: " + traderId)
    );
    List<Account> accountsToDelete = accountRepository.findByTrader(toDelete);
    accountsToDelete.forEach(a -> {
      // check balance
      if (a.getAmount() != 0) {
        throw new IllegalArgumentException(
            "Trader " + traderId + " has non-zero balance: " + a.getAmount());
      }
      // check open positions
      List<Position> positions = positionRepository.findByAccountId(a.getId());
      positions.forEach(p -> {
        if (p.getPosition() != 0) {
          throw new IllegalArgumentException(
              "Trader " + traderId + " has open position: " + p);
        }
      });
    });
    accountRepository.deleteAll(accountsToDelete);
    traderRepository.delete(toDelete);
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

  private Account findAccountById(@NotNull Integer accountId) {
    return accountRepository.findById(accountId).orElseThrow(
        () -> new IllegalArgumentException("Account ID not found: " + accountId)
    );
  }

}
