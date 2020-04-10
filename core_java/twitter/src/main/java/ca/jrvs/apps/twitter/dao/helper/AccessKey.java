package ca.jrvs.apps.twitter.dao.helper;

public class AccessKey {

  private String consumerKey;
  private String consumerSecret;
  private String accessToken;
  private String tokenSecret;

  public void loadFromEnv() {
    consumerKey = System.getenv("consumerKey");
    if (consumerKey == null) {
      throw new RuntimeException("Environment variable consumerKey not set");
    }
    consumerSecret = System.getenv("consumerSecret");
    if (consumerSecret == null) {
      throw new RuntimeException("Environment variable consumerSecret not set");
    }
    accessToken = System.getenv("accessToken");
    if (accessToken == null) {
      throw new RuntimeException("Environment variable accessToken not set");
    }
    tokenSecret = System.getenv("tokenSecret");
    if (tokenSecret == null) {
      throw new RuntimeException("Environment variable tokenSecret not set");
    }
  }

  public String getConsumerKey() {
    return consumerKey;
  }

  public String getConsumerSecret() {
    return consumerSecret;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenSecret() {
    return tokenSecret;
  }

  @Override
  public String toString() {
    return "AccessKey {" +
        " \n consumerKey='" + consumerKey + '\'' +
        ",\n consumerSecret='" + consumerSecret + '\'' +
        ",\n accessToken='" + accessToken + '\'' +
        ",\n tokenSecret='" + tokenSecret + '\'' +
        "\n}";
  }
}
