package ca.jrvs.apps.trading.model.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
@Table(name = "security_order")
public class SecurityOrder implements Entity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column
  private String status;

  @NotNull
  @Column
  private Integer size;

  @NotNull
  @Column
  private Double price;

  @NotNull
  @Column
  private String notes;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "ticker")
  private Quote quote;

  @NotNull
  @ManyToOne
  private Account account;

  public SecurityOrder() {
  }

  public SecurityOrder(String status, Integer size, Double price, String notes, Quote quote,
      Account account) {
    this.status = status;
    this.size = size;
    this.price = price;
    this.notes = notes;
    this.quote = quote;
    this.account = account;
  }

  @Override
  public String toString() {
    return "SecurityOrder{" +
        "id=" + id +
        ", status='" + status + '\'' +
        ", size=" + size +
        ", price=" + price +
        ", notes='" + notes + '\'' +
        ", ticker=" + getTicker() +
        ", accountId=" + getAccountId() +
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
    SecurityOrder that = (SecurityOrder) o;
    return id.equals(that.id) &&
        status.equals(that.status) &&
        size.equals(that.size) &&
        price.equals(that.price) &&
        notes.equals(that.notes) &&
        quote.equals(that.quote) &&
        account.equals(that.account);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, size, price, notes, quote.getId(), account.getId());
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAccountId() {
    return account.getId();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTicker() {
    return quote.getTicker();
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
