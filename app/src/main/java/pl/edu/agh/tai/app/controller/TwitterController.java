package pl.edu.agh.tai.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.tai.app.TweetException;
import pl.edu.agh.tai.app.TweetStatus;
import pl.edu.agh.tai.app.token.CustomAccessToken;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TwitterController {

    @Autowired
    private TwitterFactory twitterFactory;

    @Autowired
    private CustomAccessToken customAccessToken;

    List<TweetStatus> tweetStatuses = new ArrayList<>();
    List<TweetStatus> tweetStatusesHashtag = new ArrayList<>();
    List<Status> favourites = new ArrayList<>();
    List<Status> retweetedTweets = new ArrayList<>();

    Status status;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<TweetStatus> getAllTweets() {
        Twitter twitter = getTwitterInstance();

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

//    @ResponseBody
//    @RequestMapping(value = "/screenname", method = RequestMethod.GET, produces = "application/json")
//    public String getScreenName() {
//        Twitter twitter = getTwitterInstance();
//
//        String screenName = null;
//        try {
//            screenName = twitter.getScreenName();
//        } catch (TwitterException e) {
//            e.printStackTrace();
//        }
//        return screenName;
//    }

    @ResponseBody
    @RequestMapping(value = "/{hashtag}", method = RequestMethod.GET, produces = "application/json")
    public List<TweetStatus> searchForHashtag(@PathVariable String hashtag) {
        Twitter twitter = getTwitterInstance();

        try {
            if (!hashtag.startsWith("#")) {
                hashtag = "#" + hashtag;
            }

            QueryResult result;
            Query query = new Query(hashtag);

            do {
                result = twitter.search(query);
                for (Status tweet : result.getTweets()) {
                    TweetStatus ts = new TweetStatus(String.valueOf(tweet.getId()), tweet);
                    tweetStatusesHashtag.add(ts);
                }
            } while (((query = result.nextQuery()) != null));
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new TweetException(e);
        }

        return tweetStatusesHashtag;
    }

    @ResponseBody
    @RequestMapping(value = "/favourite", method = RequestMethod.POST, produces = "application/json")
    public List<Status> favourite(Long tweetId) {
        Twitter twitter = getTwitterInstance();

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
    public List<Status> unfavourite(Long tweetId) {
        Twitter twitter = getTwitterInstance();

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
    public List<Status> retweet(Long tweetId) {
        Twitter twitter = getTwitterInstance();

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
          Twitter twitter = getTwitterInstance();

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
    public void reply(Long tweetId, String screenName, String text) {
        Twitter twitter = getTwitterInstance();

        try {
            StatusUpdate statusUpdate = new StatusUpdate("@" + screenName + " " + text);
            statusUpdate.inReplyToStatusId(tweetId);
            twitter.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private Twitter getTwitterInstance() {
        return twitterFactory.getInstance(customAccessToken.getTwitterAccessToken());
    }
}
