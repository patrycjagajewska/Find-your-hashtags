package pl.edu.agh.tai.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TwitterController {

    private TwitterFactory factory = new TwitterFactory();
    private Twitter twitter = factory.getInstance();

    List<Status> favourites = null;
    List<Status> retweetedTweets = null;
    List<Status> comments = null;
    Status status = null;

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
    @RequestMapping(value = "/favourite", method = RequestMethod.POST, produces = "application/json")
    public List<Status> favourite(Long tweetId){

        try {
            status = twitter.createFavorite(tweetId);
            favourites.add(status);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return favourites;
    }

    @ResponseBody
    @RequestMapping(value = "/unfavourite", method = RequestMethod.POST, produces = "application/json")
    public List<Status> unfavourite(Long tweetId){

        try {
            status = twitter.destroyFavorite(tweetId);
            favourites.remove(status);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return favourites;
    }

    @ResponseBody
    @RequestMapping(value = "/retweet", method = RequestMethod.POST, produces = "application/json")
    public List<Status> retweet(Long tweetId){

        try {
            status = twitter.retweetStatus(tweetId);
            retweetedTweets.add(status);
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
