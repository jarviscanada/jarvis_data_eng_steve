package ca.jrvs.apps.trading.service;

import static ca.jrvs.apps.trading.TestUtil.getQuoteRbc;
import static ca.jrvs.apps.trading.TestUtil.getTraderDavid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Id.PositionId;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.repo.AccountRepository;
import ca.jrvs.apps.trading.repo.PositionRepository;
import ca.jrvs.apps.trading.repo.QuoteRepository;
import ca.jrvs.apps.trading.repo.SecurityOrderRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @Captor
  ArgumentCaptor<SecurityOrder> captorSecurityOrder;

  @Mock
  private AccountRepository accountRepository;
  @Mock
  private SecurityOrderRepository securityOrderRepository;
  @Mock
  private QuoteRepository quoteRepository;
  @Mock
  private PositionRepository positionRepository;

  @InjectMocks
  private OrderService service;

  private static final Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);

  @Before
  public void setUp() {
    Account account = new Account(getTraderDavid());
    account.setId(1);
    account.setAmount(1e+4);
    when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));

    Position position = new Position(1, "RY", 100);
    when(positionRepository.findById(any(PositionId.class))).thenReturn(Optional.of(position));

    Quote quote = new Quote(
        "RY", 95d, 100d, 100, 90d, 100
    );
    when(quoteRepository.findById(anyString())).thenReturn(Optional.of(getQuoteRbc()));
  }

  @Test
  public void executeBuyOrder() {
    service.executeMarketOrder(
        new MarketOrderDto(1, "RY", true, 10)
    );
    verify(securityOrderRepository).save(captorSecurityOrder.capture());
    assertEquals(10, captorSecurityOrder.getValue().getSize().intValue());

    // large order
    try {
      service.executeMarketOrder(
          new MarketOrderDto(1, "RY", true, 1000)
      );
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(e.getMessage());
    }

    // insufficient fund
    try {
      service.executeMarketOrder(
          new MarketOrderDto(1, "RY", true, 100)
      );
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(e.getMessage());
    }
  }

  @Test
  public void executeSellOrder() {
    service.executeMarketOrder(
        new MarketOrderDto(1, "RY", false, 10)
    );
    verify(securityOrderRepository).save(captorSecurityOrder.capture());
    assertEquals(-10, captorSecurityOrder.getValue().getSize().intValue());

    // insufficient holdings
    try {
      service.executeMarketOrder(
          new MarketOrderDto(1, "RY", false, 1000)
      );
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(e.getMessage());
    }

    // record unavailable
    when(positionRepository.findById(any(PositionId.class))).thenReturn(Optional.empty());
    try {
      service.executeMarketOrder(
          new MarketOrderDto(1, "RY", false, 10)
      );
      fail();
    } catch (IllegalArgumentException e) {
      logger.info(e.getMessage());
    }
  }
}