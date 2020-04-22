package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A hashtag written with a # symbol is used to index keywords or topics on Twitter.
 */
public class Hashtag {

  /**
   * An array of integers indicating the offsets within the Tweet text where the hashtag begins and
   * ends.
   */
  @JsonProperty("indices")
  private int[] indices;

  /**
   * Name of the hashtag, minus the leading '#' character.
   */
  @JsonProperty("text")
  private String text;

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
