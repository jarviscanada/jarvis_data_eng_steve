package ca.jrvs.apps.trading.model.domain;

public class QuoteBuilder {

  private String ticker;
  private Double lastPrice;
  private Double bidPrice;
  private Integer bidSize;
  private Double askPrice;
  private Integer askSize;

  public QuoteBuilder setTicker(String ticker) {
    this.ticker = ticker;
    return this;
  }

  public QuoteBuilder setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
    return this;
  }

  public QuoteBuilder setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
    return this;
  }

  public QuoteBuilder setBidSize(Integer bidSize) {
    this.bidSize = bidSize;
    return this;
  }

  public QuoteBuilder setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
    return this;
  }

  public QuoteBuilder setAskSize(Integer askSize) {
    this.askSize = askSize;
    return this;
  }

  public Quote createQuote() {
    return new Quote(ticker, lastPrice, bidPrice, bidSize, askPrice, askSize);
  }
}