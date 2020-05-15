package ca.jrvs.apps.trading.model.domain;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
public class Account implements Entity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @ManyToOne
  private Trader trader;

  @NotNull
  @Column
  private Double amount = 0d;

  @OneToMany(mappedBy = "account")
  List<SecurityOrder> orderList;

  public Account() {
  }

  public Account(Trader trader) {
    this.trader = trader;
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
