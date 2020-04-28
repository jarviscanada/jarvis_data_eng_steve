package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private final MarketDataDao marketDataDao;
  private final QuoteDao quoteDao;

  @Autowired
  public QuoteService(MarketDataDao marketDataDao, QuoteDao quoteDao) {
    this.marketDataDao = marketDataDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Find an IexQuote by given ticker
   *
   * @throws IllegalArgumentException if input ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException("Invalid ticker: " + ticker));
  }
}
