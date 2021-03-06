package ca.jrvs.apps.trading.dao.config;

public class MarketDataConfig {

  private String host;
  private String token;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    if (host == null) {
      throw new IllegalArgumentException("Input host is null");
    }
    this.host = host;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    if (token == null) {
      throw new IllegalArgumentException("Input token is null");
    }
    this.token = token;
  }
}
