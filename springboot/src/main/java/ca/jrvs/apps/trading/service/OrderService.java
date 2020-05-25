package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.repo.AccountRepository;
import ca.jrvs.apps.trading.repo.PositionRepository;
import ca.jrvs.apps.trading.repo.QuoteRepository;
import ca.jrvs.apps.trading.repo.SecurityOrderRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class OrderService extends BaseRepoService {

  @Autowired
  public OrderService(AccountRepository account, SecurityOrderRepository order,
      QuoteRepository quote, PositionRepository position) {
    this.accountRepository = account;
    this.orderRepository = order;
    this.quoteRepository = quote;
    this.positionRepository = position;
  }

  /**
   * Execute a market order
   *
   * @throws IllegalArgumentException if unable to process the request due to invalid input
   */
  public SecurityOrder executeMarketOrder(@Valid MarketOrderDto dto) {
    final SecurityOrder order = new SecurityOrder();
    final Account account = findAccountById(dto.getAccountId());
    order.setAccount(account);
    final Quote quote = findQuoteByTicker(dto.getTicker());
    order.setQuote(quote);

    if (dto.isBuyOrder()) {
      handleBuyOrder(dto, order, account);
    } else {
      handleSellOrder(dto, order, account);
    }
    accountRepository.save(account);
    quote.setLastPrice(order.getPrice());
    quoteRepository.save(quote);
    order.setStatus("FILLED");
    return orderRepository.save(order);
  }

  /**
   * Helper function that mutates account.amount, securityOrder.size, securityOrder.price
   */
  private void handleBuyOrder(MarketOrderDto dto, SecurityOrder securityOrder, Account account) {
    Quote targetQuote = securityOrder.getQuote();
    if (dto.getSize() > targetQuote.getAskSize()) {
      throw new IllegalArgumentException(
          "Your bid size is too large. Current available shares: " + targetQuote.getAskSize());
    }
    double amountDue = dto.getSize() * targetQuote.getAskPrice();
    if (account.getAmount() < amountDue) {
      throw new IllegalArgumentException(
          "Insufficient balance to process this transaction." +
              " Unit price: " + targetQuote.getAskPrice() +
              " Size required: " + dto.getSize() +
              " Amount due: " + amountDue +
              " Your balance: " + account.getAmount());
    }
    account.setAmount(account.getAmount() - amountDue);
    securityOrder.setSize(dto.getSize());
    securityOrder.setPrice(targetQuote.getAskPrice());
  }

  /**
   * Helper function that mutates account.amount, securityOrder.size, securityOrder.price
   */
  private void handleSellOrder(MarketOrderDto dto, SecurityOrder securityOrder, Account account) {
    Position position = findPositionById(securityOrder.getPositionId());
    if (dto.getSize() > position.getPosition()) {
      throw new IllegalArgumentException(
          "Your ask size exceeded the amount of shares you hold: " + position.getPosition());
    }
    double unitPrice = securityOrder.getQuote().getBidPrice();
    securityOrder.setSize(-dto.getSize());
    securityOrder.setPrice(unitPrice);
    double cash = dto.getSize() * unitPrice;
    account.setAmount(account.getAmount() + cash);
  }

}
