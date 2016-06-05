package pl.edu.agh.tai.app;

import org.springframework.web.bind.annotation.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TwitterController {

    @ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
    public List<Status> getAllTweets(@PathVariable String username) {
        try {
            TwitterFactory factory = new TwitterFactory();
            Twitter twitter = factory.getInstance();
            return twitter.getHomeTimeline();

        } catch (TwitterException e) {
            e.printStackTrace();
            throw new TweetException(e);
        }
    }
}
