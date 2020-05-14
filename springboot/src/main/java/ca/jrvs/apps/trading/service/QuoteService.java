package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.repo.QuoteRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Transactional
@Service
@Validated
public class QuoteService {

  private final MarketDataDao marketDataDao;
  private final QuoteRepository quoteRepository;

  @Autowired
  public QuoteService(MarketDataDao marketDataDao, QuoteRepository quoteRepository) {
    this.marketDataDao = marketDataDao;
    this.quoteRepository = quoteRepository;
  }

  /**
   * Find an IexQuote by given ticker
   *
   * @throws IllegalArgumentException if input ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(@NotBlank String ticker) {
    return marketDataDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException("Ticker not found: " + ticker));
  }

  /**
   * Update quote table against IEX source
   *
   * @return saved quotes
   */
  public List<Quote> updateMarketData() {
    List<String> tickers = quoteRepository.findAll()
        .stream()
        .map(Quote::getId)
        .collect(Collectors.toList());
    return marketDataDao.findAllById(tickers)
        .stream()
        .map(QuoteService::buildQuoteFromIexQuote)
        .map(quoteRepository::save)
        .collect(Collectors.toList());
  }

  /**
   * Map a IexQuote to a Quote entity
   */
  static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    return new Quote(
        iexQuote.getSymbol(),
        iexQuote.getLatestPrice(),
        iexQuote.getIexBidPrice(),
        iexQuote.getIexBidSize(),
        iexQuote.getIexAskPrice(),
        iexQuote.getIexAskSize()
    );
  }

  /**
   * Validate against IEX and save given tickers to quote table
   *
   * @param tickers a list of tickers/symbols
   * @throws IllegalArgumentException if ticker could not be found from IEX
   */
  public List<Quote> saveQuotes(@Size(min = 1) List<String> tickers) {
    return tickers.stream()
        .map(this::saveQuote)
        .collect(Collectors.toList());
  }

  /**
   * Validate against IEX and save given a ticker to quote table
   *
   * @throws IllegalArgumentException if ticker could not be found from IEX
   */
  public Quote saveQuote(@NotBlank String ticker) {
    IexQuote iexQuote = findIexQuoteByTicker(ticker);
    Quote dbQuote = buildQuoteFromIexQuote(iexQuote);
    quoteRepository.save(dbQuote);
    return dbQuote;
  }

  /**
   * Update a given quote to quote table without validation
   */
  public Quote saveQuote(@Valid Quote quote) {
    return quoteRepository.save(quote);
  }

  /**
   * Find all quotes from the quote table
   */
  public List<Quote> findAllQuotes() {
    return quoteRepository.findAll();
  }

}
