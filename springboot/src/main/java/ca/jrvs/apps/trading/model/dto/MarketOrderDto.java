package ca.jrvs.apps.trading.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class MarketOrderDto {

  @Positive
  private Integer size;

  @NotNull
  private Boolean isBuyOrder;

  @NotBlank
  private String ticker;

  @NotNull
  private Integer accountId;

  public MarketOrderDto(Integer accountId, String ticker, Boolean isBuyOrder, Integer size) {
    this.size = size;
    this.isBuyOrder = isBuyOrder;
    this.ticker = ticker;
    this.accountId = accountId;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Boolean isBuyOrder() {
    return isBuyOrder;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

}
