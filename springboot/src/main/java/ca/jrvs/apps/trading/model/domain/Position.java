package ca.jrvs.apps.trading.model.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import org.hibernate.annotations.Immutable;

@javax.persistence.Entity
@Immutable
public class Position implements Entity<Integer> {

  @Id
  @Column(name = "account_id")
  private Integer accountId;

  @Column
  private String ticker;

  @Column
  private Integer position;

  @Override
  public Integer getId() {
    return accountId;
  }

  @Override
  public void setId(Integer id) {
    this.accountId = id;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }
}