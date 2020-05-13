package ca.jrvs.apps.trading.model.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@javax.persistence.Entity
public class Account implements Entity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private Trader trader;

  @Column
  private Double amount;

  public Account() {
  }

  public Account(@Valid Trader trader, Double amount) {
    this.trader = trader;
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Account{" +
        "id=" + id +
        ", traderId=" + getTraderId() +
        ", amount=" + amount +
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
    Account account = (Account) o;
    return id.equals(account.id) &&
        trader.equals(account.trader) &&
        amount.equals(account.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, getTraderId(), amount);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTraderId() {
    return trader.getId();
  }

  public Trader getTrader() {
    return trader;
  }

  public void setTrader(@Valid Trader trader) {
    this.trader = trader;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
