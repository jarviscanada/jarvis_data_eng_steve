package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.domain.Id.PositionId;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

@javax.persistence.Entity
@Immutable
@IdClass(PositionId.class)
public class Position implements Entity<Integer> {

  @Id
  @Column(name = "account_id")
  @NotNull
  private Integer accountId;

  @Id
  @Column
  @NotBlank
  private String ticker;

  @Column
  @NotNull
  private Integer position;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position1 = (Position) o;
    return accountId.equals(position1.accountId) &&
        ticker.equals(position1.ticker) &&
        position.equals(position1.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, ticker, position);
  }

  @Override
  public String toString() {
    return "Position{" +
        "accountId=" + accountId +
        ", ticker='" + ticker + '\'' +
        ", position=" + position +
        '}';
  }

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
