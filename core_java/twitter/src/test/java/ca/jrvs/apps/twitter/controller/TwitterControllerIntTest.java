package ca.jrvs.apps.twitter.controller;

import static ca.jrvs.apps.twitter.TestUtil.HASHTAG;
import static ca.jrvs.apps.twitter.TestUtil.LATITUDE;
import static ca.jrvs.apps.twitter.TestUtil.LONGITUDE;
import static ca.jrvs.apps.twitter.TestUtil.MENTION;
import static ca.jrvs.apps.twitter.TestUtil.TEXT;
import static ca.jrvs.apps.twitter.TestUtil.checkTweet;
import static ca.jrvs.apps.twitter.TestUtil.getFieldsWithNullAndMistyped;
import static ca.jrvs.apps.twitter.utils.TweetUtil.CMD_DEL;
import static ca.jrvs.apps.twitter.utils.TweetUtil.CMD_POST;
import static ca.jrvs.apps.twitter.utils.TweetUtil.CMD_SHOW;
import static ca.jrvs.apps.twitter.utils.TweetUtil.COORD_SEP;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import ca.jrvs.apps.twitter.TestUtil;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.AccessKey;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterServiceImpl;
import ca.jrvs.apps.twitter.utils.TweetUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterControllerIntTest {

  private final Logger logger = LoggerFactory.getLogger(TwitterControllerIntTest.class);
  private Controller controller;

  @Before
  public void setUp() {
    AccessKey key = new AccessKey();
    key.loadFromEnv();
    logger.info(key.toString());
    HttpHelper helper = new TwitterHttpHelper(key);
    TwitterDao dao = new TwitterDao(helper);
    Service service = new TwitterServiceImpl(dao);
    this.controller = new TwitterController(service);
  }

  @Test
  public void postShowDelete() {
    logger.info("Testing create...");
    List<Tweet> postedTweets = IntStream.range(0, 3)
        .mapToObj(i -> i + MENTION + TEXT + HASHTAG)
        .map(str -> controller.postTweet(
            new String[]{CMD_POST, str, LATITUDE + COORD_SEP + LONGITUDE})
        )
        .collect(Collectors.toList());
    postedTweets.forEach(TestUtil::checkTweet);

    logger.info("Testing find...");
    String[] fields = getFieldsWithNullAndMistyped();
    String fieldsString = TweetUtil.fieldsToString(fields);
    logger.info(fieldsString);

    List<Tweet> lastTweets = postedTweets.stream()
        .map(Tweet::getIdString)
        .map(id -> controller.showTweet(
            new String[]{CMD_SHOW, id, fieldsString}
        ))
        .collect(Collectors.toList());
    lastTweets.forEach(t -> {
      checkTweet(t);
      assertNull(t.getCreateAt());
      assertNull(t.getId());
    });

    logger.info("Testing delete...");
    List<String> deleteArgs = new ArrayList<>();
    deleteArgs.add(CMD_DEL);
    List<String> ids = (lastTweets.stream()
        .map(Tweet::getIdString)
        .collect(Collectors.toList()));
    deleteArgs.addAll(ids);

    List<Tweet> removedTweets = controller.deleteTweet(deleteArgs.toArray(new String[0]));
    removedTweets.forEach(TestUtil::checkTweet);

    // Do they still exist?
    for (String id : ids) {
      try {
        controller.showTweet(new String[]{CMD_SHOW, id});
        fail();
      } catch (RuntimeException e) {
        logger.info(id + " should be deleted, expect 404");
        logger.info(e.getMessage());
      }
    }
  }

}