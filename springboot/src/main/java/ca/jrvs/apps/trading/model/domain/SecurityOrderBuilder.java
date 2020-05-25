package ca.jrvs.apps.trading.model.domain;

public class SecurityOrderBuilder {

  private String status;
  private Integer size;
  private Double price;
  private String notes;
  private Quote quote;
  private Account account;

  public SecurityOrderBuilder setStatus(String status) {
    this.status = status;
    return this;
  }

  public SecurityOrderBuilder setSize(Integer size) {
    this.size = size;
    return this;
  }

  public SecurityOrderBuilder setPrice(Double price) {
    this.price = price;
    return this;
  }

  public SecurityOrderBuilder setNotes(String notes) {
    this.notes = notes;
    return this;
  }

  public SecurityOrderBuilder setQuote(Quote quote) {
    this.quote = quote;
    return this;
  }

  public SecurityOrderBuilder setAccount(Account account) {
    this.account = account;
    return this;
  }

  public SecurityOrder createSecurityOrder() {
    return new SecurityOrder(status, size, price, notes, quote, account);
  }
}