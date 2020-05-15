package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://iexcloud.io/docs/api/#quote
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IexQuote {

  /**
   * Refers to the stock ticker.
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Refers to the company name.
   */
  @JsonProperty("companyName")
  private String companyName;

  /**
   * Refers to the source of the latest price. Possible values are "tops", "sip", "previousclose" or
   * "close"
   */
  @JsonProperty("calculationPrice")
  private String calculationPrice;

  /**
   * Refers to the official open price from the SIP. 15 minute delayed (can be null after 00:00 ET,
   * before 9:45 and weekends)
   */
  @JsonProperty("open")
  private Double open;

  /**
   * Refers to the official listing exchange time for the open from the SIP. 15 minute delayed
   */
  @JsonProperty("openTime")
  private Long openTime;

  /**
   * Refers to the official close price from the SIP. 15 minute delayed
   */
  @JsonProperty("close")
  private Double close;

  /**
   * Refers to the official listing exchange time for the close from the SIP. 15 minute delayed
   */
  @JsonProperty("closeTime")
  private Long closeTime;

  /**
   * Refers to the market-wide highest price from the SIP. 15 minute delayed during normal market
   * hours 9:30 - 16:00 (null before 9:45 and weekends).
   */
  @JsonProperty("high")
  private Double high;

  /**
   * Refers to the market-wide lowest price from the SIP. 15 minute delayed during normal market
   * hours 9:30 - 16:00 (null before 9:45 and weekends).
   */
  @JsonProperty("low")
  private Double low;

  /**
   * Use this to get the latest price
   */
  @JsonProperty("latestPrice")
  private Double latestPrice;

  /**
   * This will represent a human readable description of the source of latestPrice.
   *
   * @see IexQuote#latestPrice
   */
  @JsonProperty("latestSource")
  private String latestSource;

  /**
   * Refers to a human readable time/date of when latestPrice was last updated. The format will vary
   * based on latestSource is inteded to be displayed to a user. Use latestUpdate for machine
   * readable timestamp.
   *
   * @see IexQuote#latestSource
   * @see IexQuote#latestUpdate
   */
  @JsonProperty("latestTime")
  private String latestTime;

  /**
   * Refers to the machine readable epoch timestamp of when latestPrice was last updated.
   * Represented in milliseconds since midnight Jan 1, 1970.
   */
  @JsonProperty("latestUpdate")
  private Long latestUpdate;

  /**
   * Use this to get the latest volume
   */
  @JsonProperty("latestVolume")
  private Long latestVolume;

  /**
   * Total volume for the stock, but only updated after market open. To get pre-market volume, use
   * latestVolume
   *
   * @see IexQuote#latestVolume
   */
  @JsonProperty("volume")
  private Long volume;

  /**
   * Refers to the price of the last trade on IEX.
   */
  @JsonProperty("iexRealtimePrice")
  private Double iexRealtimePrice;

  /**
   * Refers to the size of the last trade on IEX.
   */
  @JsonProperty("iexRealtimeSize")
  private Integer iexRealtimeSize;

  /**
   * Refers to the last update time of iexRealtimePrice in milliseconds since midnight Jan 1, 1970
   * UTC or -1 or 0. If the value is -1 or 0, IEX has not quoted the symbol in the trading day.
   */
  @JsonProperty("iexLastUpdated")
  private Long iexLastUpdated;

  /**
   * Refers to the 15 minute delayed market price from the SIP during normal market hours 9:30 -
   * 16:00 ET.
   */
  @JsonProperty("delayedPrice")
  private Double delayedPrice;

  /**
   * Refers to the last update time of the delayed market price during normal market hours 9:30 -
   * 16:00 ET.
   */
  @JsonProperty("delayedPriceTime")
  private Long delayedPriceTime;

  /**
   * Refers to the 15 minute delayed odd Lot trade price from the SIP during normal market hours
   * 9:30 - 16:00 ET.
   */
  @JsonProperty("oddLotDelayedPrice")
  private Double oddLotDelayedPrice;

  /**
   * Refers to the last update time of the odd Lot trade price during normal market hours 9:30 -
   * 16:00 ET.
   */
  @JsonProperty("oddLotDelayedPriceTime")
  private Long oddLotDelayedPriceTime;

  /**
   * Refers to the 15 minute delayed price outside normal market hours 0400 - 0930 ET and 1600 -
   * 2000 ET. This provides pre market and post market price. This is purposefully separate from
   * latestPrice so users can display the two prices separately.
   *
   * @see IexQuote#latestPrice
   */
  @JsonProperty("extendedPrice")
  private Double extendedPrice;

  /**
   * Refers to the price change between extendedPrice and latestPrice.
   */
  @JsonProperty("extendedChange")
  private Double extendedChange;

  /**
   * Refers to the price change percent between extendedPrice and latestPrice.
   */
  @JsonProperty("extendedChangePercent")
  private Double extendedChangePercent;

  /**
   * Refers to the last update time of extendedPrice.
   *
   * @see IexQuote#extendedPrice
   */
  @JsonProperty("extendedPriceTime")
  private Long extendedPriceTime;

  /**
   * Refers to the previous trading day closing price.
   */
  @JsonProperty("previousClose")
  private Double previousClose;

  /**
   * Refers to the previous trading day volume.
   */
  @JsonProperty("previousVolume")
  private Long previousVolume;

  /**
   * Refers to the change in price between latestPrice and previousClose.
   */
  @JsonProperty("change")
  private Double change;

  /**
   * efers to the percent change in price between latestPrice and previousClose. For example, a 5%
   * change would be represented as 0.05.
   */
  @JsonProperty("changePercent")
  private Double changePercent;

  /**
   * Refers to IEX’s percentage of the market in the stock.
   */
  @JsonProperty("iexMarketPercent")
  private Double iexMarketPercent;

  /**
   * Refers to shares traded in the stock on IEX.
   */
  @JsonProperty("iexVolume")
  private Long iexVolume;

  /**
   * Refers to the 30 day average volume.
   */
  @JsonProperty("avgTotalVolume")
  private Long avgTotalVolume;

  /**
   * Refers to the best bid price on IEX.
   */
  @JsonProperty("iexBidPrice")
  private Double iexBidPrice;

  /**
   * Refers to amount of shares on the bid on IEX.
   */
  @JsonProperty("iexBidSize")
  private Integer iexBidSize;

  /**
   * Refers to the best ask price on IEX.
   */
  @JsonProperty("iexAskPrice")
  private Double iexAskPrice;

  /**
   * Refers to amount of shares on the ask on IEX.
   */
  @JsonProperty("iexAskSize")
  private Integer iexAskSize;

  /**
   * is calculated in real time using latestPrice.
   */
  @JsonProperty("marketCap")
  private Long marketCap;

  /**
   * Refers to the adjusted 52 week high.
   */
  @JsonProperty("week52High")
  private Double week52High;

  /**
   * Refers to the adjusted 52 week low.
   */
  @JsonProperty("week52Low")
  private Double week52Low;

  /**
   * Refers to the price change percentage from start of year to previous close.
   */
  @JsonProperty("ytdChange")
  private Double ytdChange;

  /**
   * Refers to the price-to-earnings ratio for the company.
   */
  @JsonProperty("peRatio")
  private Double peRatio;

  /**
   * Epoch timestamp in milliseconds of the last market hours trade excluding the closing auction
   * trade.
   */
  @JsonProperty("lastTradeTime")
  private Long lastTradeTime;

  /**
   * For US stocks, indicates if the market is in normal market hours. Will be false during extended
   * hours trading.
   */
  @JsonProperty("isUSMarketOpen")
  private Boolean isUSMarketOpen;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCalculationPrice() {
    return calculationPrice;
  }

  public void setCalculationPrice(String calculationPrice) {
    this.calculationPrice = calculationPrice;
  }

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Long getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Long openTime) {
    this.openTime = openTime;
  }

  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  public Long getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(Long closeTime) {
    this.closeTime = closeTime;
  }

  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public String getLatestSource() {
    return latestSource;
  }

  public void setLatestSource(String latestSource) {
    this.latestSource = latestSource;
  }

  public String getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(String latestTime) {
    this.latestTime = latestTime;
  }

  public Long getLatestUpdate() {
    return latestUpdate;
  }

  public void setLatestUpdate(Long latestUpdate) {
    this.latestUpdate = latestUpdate;
  }

  public Long getLatestVolume() {
    return latestVolume;
  }

  public void setLatestVolume(Long latestVolume) {
    this.latestVolume = latestVolume;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  public Double getIexRealtimePrice() {
    return iexRealtimePrice;
  }

  public void setIexRealtimePrice(Double iexRealtimePrice) {
    this.iexRealtimePrice = iexRealtimePrice;
  }

  public Integer getIexRealtimeSize() {
    return iexRealtimeSize;
  }

  public void setIexRealtimeSize(Integer iexRealtimeSize) {
    this.iexRealtimeSize = iexRealtimeSize;
  }

  public Long getIexLastUpdated() {
    return iexLastUpdated;
  }

  public void setIexLastUpdated(Long iexLastUpdated) {
    this.iexLastUpdated = iexLastUpdated;
  }

  public Double getDelayedPrice() {
    return delayedPrice;
  }

  public void setDelayedPrice(Double delayedPrice) {
    this.delayedPrice = delayedPrice;
  }

  public Long getDelayedPriceTime() {
    return delayedPriceTime;
  }

  public void setDelayedPriceTime(Long delayedPriceTime) {
    this.delayedPriceTime = delayedPriceTime;
  }

  public Double getOddLotDelayedPrice() {
    return oddLotDelayedPrice;
  }

  public void setOddLotDelayedPrice(Double oddLotDelayedPrice) {
    this.oddLotDelayedPrice = oddLotDelayedPrice;
  }

  public Long getOddLotDelayedPriceTime() {
    return oddLotDelayedPriceTime;
  }

  public void setOddLotDelayedPriceTime(Long oddLotDelayedPriceTime) {
    this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
  }

  public Double getExtendedPrice() {
    return extendedPrice;
  }

  public void setExtendedPrice(Double extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public Double getExtendedChange() {
    return extendedChange;
  }

  public void setExtendedChange(Double extendedChange) {
    this.extendedChange = extendedChange;
  }

  public Double getExtendedChangePercent() {
    return extendedChangePercent;
  }

  public void setExtendedChangePercent(Double extendedChangePercent) {
    this.extendedChangePercent = extendedChangePercent;
  }

  public Long getExtendedPriceTime() {
    return extendedPriceTime;
  }

  public void setExtendedPriceTime(Long extendedPriceTime) {
    this.extendedPriceTime = extendedPriceTime;
  }

  public Double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(Double previousClose) {
    this.previousClose = previousClose;
  }

  public Long getPreviousVolume() {
    return previousVolume;
  }

  public void setPreviousVolume(Long previousVolume) {
    this.previousVolume = previousVolume;
  }

  public Double getChange() {
    return change;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public Double getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(Double changePercent) {
    this.changePercent = changePercent;
  }

  public Double getIexMarketPercent() {
    return iexMarketPercent;
  }

  public void setIexMarketPercent(Double iexMarketPercent) {
    this.iexMarketPercent = iexMarketPercent;
  }

  public Long getIexVolume() {
    return iexVolume;
  }

  public void setIexVolume(Long iexVolume) {
    this.iexVolume = iexVolume;
  }

  public Long getAvgTotalVolume() {
    return avgTotalVolume;
  }

  public void setAvgTotalVolume(Long avgTotalVolume) {
    this.avgTotalVolume = avgTotalVolume;
  }

  public Double getIexBidPrice() {
    return iexBidPrice;
  }

  public void setIexBidPrice(Double iexBidPrice) {
    this.iexBidPrice = iexBidPrice;
  }

  public Integer getIexBidSize() {
    return iexBidSize;
  }

  public void setIexBidSize(Integer iexBidSize) {
    this.iexBidSize = iexBidSize;
  }

  public Double getIexAskPrice() {
    return iexAskPrice;
  }

  public void setIexAskPrice(Double iexAskPrice) {
    this.iexAskPrice = iexAskPrice;
  }

  public Integer getIexAskSize() {
    return iexAskSize;
  }

  public void setIexAskSize(Integer iexAskSize) {
    this.iexAskSize = iexAskSize;
  }

  public Long getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(Long marketCap) {
    this.marketCap = marketCap;
  }

  public Double getWeek52High() {
    return week52High;
  }

  public void setWeek52High(Double week52High) {
    this.week52High = week52High;
  }

  public Double getWeek52Low() {
    return week52Low;
  }

  public void setWeek52Low(Double week52Low) {
    this.week52Low = week52Low;
  }

  public Double getYtdChange() {
    return ytdChange;
  }

  public void setYtdChange(Double ytdChange) {
    this.ytdChange = ytdChange;
  }

  public Double getPeRatio() {
    return peRatio;
  }

  public void setPeRatio(Double peRatio) {
    this.peRatio = peRatio;
  }

  public Long getLastTradeTime() {
    return lastTradeTime;
  }

  public void setLastTradeTime(Long lastTradeTime) {
    this.lastTradeTime = lastTradeTime;
  }

  public Boolean getUSMarketOpen() {
    return isUSMarketOpen;
  }

  public void setUSMarketOpen(Boolean USMarketOpen) {
    isUSMarketOpen = USMarketOpen;
  }
}
