package ca.jrvs.apps.twitter.dao.helper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AccessKey loads and stores all necessary information for authentication
 */
public class AccessKey {

  @JsonProperty("consumer_key")
  private String consumerKey;
  @JsonProperty("consumer_secret")
  private String consumerSecret;
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("token_secret")
  private String tokenSecret;

  public AccessKey(String consumerKey, String consumerSecret,
      String accessToken, String tokenSecret) {
    this.consumerKey = consumerKey;
    this.consumerSecret = consumerSecret;
    this.accessToken = accessToken;
    this.tokenSecret = tokenSecret;
    validate();
  }

  /**
   * Load secret information from environment variables
   */
  public void loadFromEnv() {
    consumerKey = System.getenv("consumerKey");
    consumerSecret = System.getenv("consumerSecret");
    accessToken = System.getenv("accessToken");
    tokenSecret = System.getenv("tokenSecret");
    validate();
  }

  private void validate() {
    if (consumerKey == null) {
      throw new RuntimeException("Empty consumerKey");
    }
    if (consumerSecret == null) {
      throw new RuntimeException("Empty consumerSecret");
    }
    if (accessToken == null) {
      throw new RuntimeException("Empty accessToken");
    }
    if (tokenSecret == null) {
      throw new RuntimeException("Empty tokenSecret");
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
