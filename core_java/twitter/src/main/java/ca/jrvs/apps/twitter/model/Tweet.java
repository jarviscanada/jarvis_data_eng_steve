package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simplified version of the Tweet object
 *
 * @see <a href="https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object">
 * Tweet Object</a>
 */
public class Tweet {

  /**
   * UTC time when this Tweet was created.
   */
  @JsonProperty("create_at")
  private String createAt;

  /**
   * The integer representation of the unique identifier for this Tweet.
   */
  @JsonProperty("id")
  private long id;

  /**
   * The string representation of the unique identifier for this Tweet.
   */
  @JsonProperty("id_str")
  private String idString;

  /**
   * The actual UTF-8 text of the status update.
   */
  @JsonProperty("text")
  private String text;

  /**
   * Entities which have been parsed out of the text of the Tweet.
   *
   * @see Entities
   */
  @JsonProperty("entities")
  private Entities entities;

  /**
   * Represents the geographic location of this Tweet as reported by the user or client application.
   * Nullable
   *
   * @see Coordinates
   */
  @JsonProperty("coordinates")
  private Coordinates coordinates;

  /**
   * Number of times this Tweet has been retweeted.
   */
  @JsonProperty("retweet_count")
  private int retweetCount;

  /**
   * Indicates approximately how many times this Tweet has been liked by Twitter users. Nullable
   */
  @JsonProperty("favorite_count")
  private Integer favoriteCount;

  /**
   * Indicates whether this Tweet has been liked by the authenticating user. Nullable
   */
  @JsonProperty("favorited")
  private Boolean favorited;
  /**
   * Indicates whether this Tweet has been Retweeted by the authenticating user.
   */
  @JsonProperty("retweeted")
  private boolean retweeted;

  public String getCreateAt() {
    return createAt;
  }

  public long getId() {
    return id;
  }

  public String getIdString() {
    return idString;
  }

  public String getText() {
    return text;
  }

  public Entities getEntities() {
    return entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public Integer getFavoriteCount() {
    return favoriteCount;
  }

  public Boolean getFavorited() {
    return favorited;
  }

  public boolean isRetweeted() {
    return retweeted;
  }

}
