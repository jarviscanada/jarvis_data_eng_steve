package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterServiceImpl implements Service {

  private static final String[] ALL_FIELDS = {
      "created_at",
      "id",
      "id_str",
      "text",
      "coordinates",
      "entities",
      "retweet_count",
      "favorite_count",
      "favorited",
      "retweeted"
  };

  private final TwitterDao dao;
  private final Logger logger = LoggerFactory.getLogger(TwitterServiceImpl.class);

  public TwitterServiceImpl(TwitterDao dao) {
    this.dao = dao;
  }

  public static String[] getAllFields() {
    return ALL_FIELDS.clone();
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    TweetUtil.validateTweet(tweet);
    return dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    TweetUtil.validateId(id);
    Tweet tweet = dao.findById(id);
    if (fields != null) {
      // validate input fields
      Arrays.stream(fields)
          .filter(Objects::nonNull)
          .forEach(y -> {
            if (Arrays.stream(ALL_FIELDS).noneMatch(y::equals)) {
              logger.warn("No such a field: " + y);
            }
          });
      // clear the fields not in fields[]
      Arrays.stream(ALL_FIELDS)
          .filter(x -> Arrays.stream(fields).noneMatch(x::equals))
          .forEach(x -> TweetUtil.clearField(tweet, x));
    }
    return tweet;
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    for (String id : ids) {
      TweetUtil.validateId(id);
    }
    return Arrays.stream(ids)
        .map(dao::deleteById)
        .collect(Collectors.toList());
  }
}