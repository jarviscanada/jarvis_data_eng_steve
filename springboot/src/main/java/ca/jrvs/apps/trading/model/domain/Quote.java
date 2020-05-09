package ca.jrvs.apps.trading.model.domain;

public class Quote implements Entity<String> {

  private String ticker;
  private Double lastPrice;
  private Double bidPrice;
  private Integer bidSize;
  private Double askPrice;
  private Integer askSize;

  public Quote() {
  }

  public Quote(String ticker, Double lastPrice, Double bidPrice, Integer bidSize,
      Double askPrice, Integer askSize) {
    this.ticker = ticker;
    this.lastPrice = lastPrice;
    this.bidPrice = bidPrice;
    this.bidSize = bidSize;
    this.askPrice = askPrice;
    this.askSize = askSize;
  }

  @Override
  public String toString() {
    return "Quote{" +
        "ticker='" + ticker + '\'' +
        ", lastPrice=" + lastPrice +
        ", bidPrice=" + bidPrice +
        ", bidSize=" + bidSize +
        ", askPrice=" + askPrice +
        ", askSize=" + askSize +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Quote quote = (Quote) o;
    return ticker.equals(quote.ticker) &&
        lastPrice.equals(quote.lastPrice) &&
        bidPrice.equals(quote.bidPrice) &&
        bidSize.equals(quote.bidSize) &&
        askPrice.equals(quote.askPrice) &&
        askSize.equals(quote.askSize);
  }

  @Override
  public String getID() {
    return ticker;
  }

  @Override
  public void setId(String s) {
    this.ticker = s;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public Integer getBidSize() {
    return bidSize;
  }

  public void setBidSize(Integer bidSize) {
    this.bidSize = bidSize;
  }

  public Double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  public Integer getAskSize() {
    return askSize;
  }

  public void setAskSize(Integer askSize) {
    this.askSize = askSize;
  }
}
