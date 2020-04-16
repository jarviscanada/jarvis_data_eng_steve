package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private final CrdDao<Tweet, String> dao;
  private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

  @Autowired
  public TwitterService(CrdDao<Tweet, String> dao) {
    this.dao = dao;
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
            if (Arrays.stream(TweetUtil.getAllFields()).noneMatch(y::equals)) {
              logger.warn("No such a field: " + y);
            }
          });
      // clear the fields not in fields[]
      Arrays.stream(TweetUtil.getAllFields())
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