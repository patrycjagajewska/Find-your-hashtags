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

    List<Status> favourites = null;
    List<Status> retweetedTweets = null;
    List<Status> comments = null;
    Status r;

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
    public List<Status> favourite(@PathVariable Long tweetId){

        try {
            r = twitter.createFavorite(tweetId);
            favourites.add(r);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return favourites;
    }

    @ResponseBody
    @RequestMapping(value = "/unfavourite/{tweetId}", method = RequestMethod.POST, produces = "application/json")
    public List<Status> unfavourite(@PathVariable Long tweetId){

        try {
            r = twitter.destroyFavorite(tweetId);
            favourites.remove(r);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return favourites;
    }

    @ResponseBody
    @RequestMapping(value = "/retweet/{tweetId}", method = RequestMethod.POST, produces = "application/json")
    public List<Status> retweet(@PathVariable Long tweetId){

        try {
            r = twitter.retweetStatus(tweetId);
            retweetedTweets.add(r);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return retweetedTweets;
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{tweetId}", method = RequestMethod.GET, produces = "application/json")
    public List<Status> getComments(){



        return comments;
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{tweetId}", method = RequestMethod.PUT, produces = "application/json")
    public void comment(@PathVariable Long tweetId, String text){

        try {
            StatusUpdate statusUpdate = new StatusUpdate(text);
            statusUpdate.inReplyToStatusId(tweetId);
            twitter.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
