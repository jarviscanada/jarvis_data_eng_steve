package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMention {

  /**
   * The name of the referenced user, as they’ve defined it.
   */
  @JsonProperty("name")
  private String name;

  /**
   * The screen name, handle, or alias that this user identifies themselves with; unique but subject
   * to change.
   */
  @JsonProperty("screen_name")
  private String screenName;

  /**
   * ID of the mentioned user, as an integer.
   */
  @JsonProperty("id")
  private long id;

  /**
   * The string representation of the ID of the mentioned user.
   */
  @JsonProperty("id_str")
  private String idString;
  /**
   * An array of integers representing the offsets within the Tweet text where the user reference
   * begins and ends.
   */
  @JsonProperty("indices")
  private int[] indices;

  public String getName() {
    return name;
  }

  public String getScreenName() {
    return screenName;
  }

  public long getId() {
    return id;
  }

  public String getIdString() {
    return idString;
  }

  public int[] getIndices() {
    return indices;
  }
}
