package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {

  /**
   * Build a Tweet object given text and coordinates information
   */
  public static Tweet buildTweet(String text, double longitude, double latitude) {
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Coordinates coordinate = new Coordinates();
    coordinate.setLatitude(latitude);
    coordinate.setLongitude(longitude);
    tweet.setCoordinates(coordinate);
    return tweet;
  }
}
