package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Id.PositionId;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.repo.AccountRepository;
import ca.jrvs.apps.trading.repo.PositionRepository;
import ca.jrvs.apps.trading.repo.QuoteRepository;
import ca.jrvs.apps.trading.repo.SecurityOrderRepository;
import ca.jrvs.apps.trading.repo.TraderRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseRepoService {

  TraderRepository traderRepository;
  PositionRepository positionRepository;
  AccountRepository accountRepository;
  QuoteRepository quoteRepository;
  SecurityOrderRepository orderRepository;

  Account findAccountById(Integer accountId) {
    return accountRepository.findById(accountId).orElseThrow(
        () -> new IllegalArgumentException("Account ID not found: " + accountId)
    );
  }

  Trader findTraderById(Integer traderId) {
    return traderRepository.findById(traderId).orElseThrow(
        () -> new IllegalArgumentException("Trader ID not found: " + traderId)
    );
  }

  Quote findQuoteByTicker(String ticker) {
    return quoteRepository.findById(ticker).orElseThrow(
        () -> new IllegalArgumentException("Quote not found: " + ticker)
    );
  }

  Position findPositionById(PositionId id) {
    return positionRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException(
            "Position of " + id + " not found. Have you previously purchased this security?")
    );
  }
}
