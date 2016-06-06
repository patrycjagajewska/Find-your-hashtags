package pl.edu.agh.tai.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tweets")
public class TwitterController {

    private TwitterFactory factory = new TwitterFactory();
    private Twitter twitter = factory.getInstance();

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Status> getAllTweets() {

        try {
            return twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new TweetException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{hashtag}", method = RequestMethod.GET, produces = "application/json")
    public List<Status> searchForHashtag(@PathVariable String hashtag){

        List<Status> tweets = null;
//        int i = 0;

        try {
            if(!hashtag.startsWith("#")){
                hashtag = "#" + hashtag;
            }

            QueryResult result;
            Query query = new Query(hashtag);

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

    @ResponseBody
    @RequestMapping(value = "/favourite/{tweetId}", method = RequestMethod.POST, produces = "application/json")
    public List<Status> markAsFavourite(@PathVariable Long tweetId){

        List<Status> favourites = null;

        try {
            twitter.createFavorite(tweetId);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return favourites;
    }

    @ResponseBody
    @RequestMapping(value = "/retweet/{tweetId}", method = RequestMethod.POST, produces = "application/json")
    public List<Status> retweet(@PathVariable Long tweetId){

        List<Status> retweetedTweets = null;

        try {
            twitter.retweetStatus(tweetId);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return retweetedTweets;
    }
}
