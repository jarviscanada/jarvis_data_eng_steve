package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {

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
