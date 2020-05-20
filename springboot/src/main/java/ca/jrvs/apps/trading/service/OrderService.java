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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Transactional
@Service
@Validated
public class OrderService {

  private final AccountRepository accountRepository;
  private final SecurityOrderRepository orderRepository;
  private final QuoteRepository quoteRepository;
  private final PositionRepository positionRepository;

  @Autowired
  public OrderService(
      AccountRepository accountRepository,
      SecurityOrderRepository orderRepository,
      QuoteRepository quoteRepository,
      PositionRepository positionRepository) {
    this.accountRepository = accountRepository;
    this.orderRepository = orderRepository;
    this.quoteRepository = quoteRepository;
    this.positionRepository = positionRepository;
  }

  /**
   * Execute a market order
   *
   * @throws IllegalArgumentException if unable to process the request due to invalid input
   */
  public SecurityOrder executeMarketOrder(@Valid MarketOrderDto dto) {
    final SecurityOrder order = new SecurityOrder();
    final Account account = accountRepository.findById(dto.getAccountId())
        .orElseThrow(() -> new IllegalArgumentException("Account NOT exists"));
    order.setAccount(account);
    final Quote quote = quoteRepository.findById(dto.getTicker())
        .orElseThrow(() -> new IllegalArgumentException("Quote Not exists"));
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
    Position position = positionRepository.findById(securityOrder.getPositionId())
        .orElseThrow(() -> new IllegalArgumentException(
            "Position not found. Have you previously purchased this security?")
        );
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
