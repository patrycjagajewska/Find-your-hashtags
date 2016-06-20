package pl.edu.agh.tai.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;
import twitter4j.auth.OAuth2Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tweets")
public class TwitterController {

    private TwitterFactory factory = new TwitterFactory();
    private Twitter twitter = factory.getInstance();

    List<TweetStatus> tweetStatuses = new ArrayList<>();
    List<Status> favourites = new ArrayList<>();
    List<Status> retweetedTweets = new ArrayList<>();

    Status status;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<TweetStatus> getAllTweets() {

        try {
            List<Status> tweets = twitter.getHomeTimeline();
            for (Status tweet : tweets) {
                TweetStatus ts = new TweetStatus(String.valueOf(tweet.getId()), tweet);
                tweetStatuses.add(ts);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new TweetException(e);
        }
        return tweetStatuses;
    }

    @ResponseBody
    @RequestMapping(value = "/{hashtag}", method = RequestMethod.GET, produces = "application/json")
    public List<Status> searchForHashtag(@PathVariable String hashtag){

        List<Status> tweets;

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
    @RequestMapping(value = "/undoretweet", method = RequestMethod.POST, produces = "application/json")
    public List<Status> undoRetweet(Long tweetId){

        try {
            List<Status> retweets = twitter.getRetweets(tweetId);
            for (Status retweet : retweets) {
                if(retweet.getRetweetedStatus().getUser().getScreenName().equals(twitter.getScreenName()))
                    twitter.destroyStatus(retweet.getId());
                    retweetedTweets.remove(retweet);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return retweetedTweets;
    }




    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.PUT, produces = "application/json")
    public void reply(Long tweetId, String screenName, String text){

        try {
            StatusUpdate statusUpdate = new StatusUpdate("@" + screenName + " " + text);
            statusUpdate.inReplyToStatusId(tweetId);
            twitter.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
