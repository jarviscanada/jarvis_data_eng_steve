package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simplified version of the Tweet object
 *
 * @see <a href="https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object">
 * Tweet Object</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {

  /**
   * UTC time when this Tweet was created.
   */
  @JsonProperty("created_at")
  private String createdAt;
  /**
   * The integer representation of the unique identifier for this Tweet.
   */
  @JsonProperty("id")
  private Long id;
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
  private Integer retweetCount;
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
  private Boolean retweeted;

  public String getCreateAt() {
    return createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(Integer retweetCount) {
    this.retweetCount = retweetCount;
  }

  public String getIdString() {
    return idString;
  }

  public void setIdString(String idString) {
    this.idString = idString;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Integer getFavoriteCount() {
    return favoriteCount;
  }

  public void setFavoriteCount(Integer favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  public Boolean getFavorited() {
    return favorited;
  }

  public void setFavorited(Boolean favorited) {
    this.favorited = favorited;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Boolean getRetweeted() {
    return retweeted;
  }

  public void setRetweeted(Boolean retweeted) {
    this.retweeted = retweeted;
  }

}
