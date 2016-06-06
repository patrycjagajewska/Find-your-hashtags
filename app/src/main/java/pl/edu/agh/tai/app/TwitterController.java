package pl.edu.agh.tai.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tweets")
public class TwitterController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Status> getAllTweets() {

        try {
            TwitterFactory factory = new TwitterFactory();
            Twitter twitter = factory.getInstance();
            return twitter.getHomeTimeline();

        } catch (TwitterException e) {
            e.printStackTrace();
            throw new TweetException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{hashtag}", method = RequestMethod.GET, produces = "application/json")
    public List<Status> searchForHashtag(@PathVariable String hashtag){

        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();

        List<Status> tweets = null;

        try {
            if(!hashtag.startsWith("#")){
                hashtag = "#" + hashtag;
            }

            QueryResult result;
            Query query = new Query(hashtag);
            int i = 0;
            do{
                result = twitter.search(query);
                tweets = result.getTweets();
            } while(((query = result.nextQuery()) != null));
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new TweetException(e);
        }

        return tweets;
    }
}
