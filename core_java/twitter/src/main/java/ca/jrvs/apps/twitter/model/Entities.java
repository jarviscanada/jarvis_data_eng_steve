package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A collection of common entities found in Tweets, including hashtags and user mentions.
 *
 * @see <a href="https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/entities-object">
 * Entities Object</a>
 */
public class Entities {

  /**
   * The entities section will contain a hashtags array containing an object for every hashtag
   * included in the Tweet body, and include an empty array if no hashtags are present.
   *
   * @see Hashtag
   */
  @JsonProperty("hashtags")
  private Hashtag[] hashtags;
  /**
   * The entities section will contain a user_mentions array containing an object for every user
   * mention included in the Tweet body, and include an empty array if no user mention is present.
   *
   * @see UserMention
   */
  @JsonProperty("user_mentions")
  private UserMention[] mentions;

  public Hashtag[] getHashtags() {
    return hashtags;
  }

  public UserMention[] getMentions() {
    return mentions;
  }
}
