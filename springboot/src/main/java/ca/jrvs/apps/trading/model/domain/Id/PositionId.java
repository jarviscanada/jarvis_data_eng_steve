package ca.jrvs.apps.trading.model.domain.Id;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PositionId implements Serializable {

  @NotNull
  private Integer accountId;
  @NotBlank
  private String ticker;

  public PositionId() {
  }

  public PositionId(@NotNull Integer accountId, @NotBlank String ticker) {
    this.accountId = accountId;
    this.ticker = ticker;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PositionId that = (PositionId) o;
    return accountId.equals(that.accountId) &&
        ticker.equals(that.ticker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, ticker);
  }
}
