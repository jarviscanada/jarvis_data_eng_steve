package ca.jrvs.apps.trading.model.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.Entity
public class Account implements Entity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "trader_id")
  private Integer traderId;

  @Column
  private Double amount;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTraderId() {
    return traderId;
  }

  public void setTraderId(Integer traderId) {
    this.traderId = traderId;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
