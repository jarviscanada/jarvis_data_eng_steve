package ca.jrvs.apps.twitter.controller;

import static ca.jrvs.apps.twitter.utils.TweetUtil.COORD_SEP;
import static ca.jrvs.apps.twitter.utils.TweetUtil.FIELD_SEP;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

  private static final String POST_USAGE =
      "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"";
  private static final String SHOW_USAGE =
      "USAGE: TwitterCLIApp show tweet_id [\"field1, field2, ...\"]";

  private final Service service;

  @Autowired
  public TwitterController(Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(
          "Incorrect number of args\n" + POST_USAGE
      );
    }
    String text = args[1];
    String coordinateString = args[2];
    String[] coordinateArray = coordinateString.split(COORD_SEP);
    if (coordinateArray.length != 2) {
      throw new IllegalArgumentException(
          "Invalid location format\n" + POST_USAGE
      );
    }

    Double lat, lon;
    try {
      lat = Double.parseDouble(coordinateArray[0]);
      lon = Double.parseDouble(coordinateArray[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Invalid location format\n" + POST_USAGE, e
      );
    }
    return service.postTweet(TweetUtil.buildTweet(text, lon, lat));
  }

  @Override
  public Tweet showTweet(String[] args) {
    if (args.length < 2 || args.length > 3) {
      throw new IllegalArgumentException(
          "Incorrect number of args\n" + SHOW_USAGE
      );
    }
    String id = args[1];
    String[] fieldsArray = null;
    if (args.length == 3) {
      String fieldsString = args[2];
      fieldsArray = fieldsString.split(FIELD_SEP);
    }

    return service.showTweet(id, fieldsArray);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length < 2) {
      throw new IllegalArgumentException("At least one id should be given");
    }
    return service.deleteTweets(Arrays.copyOfRange(args, 1, args.length));
  }
}
