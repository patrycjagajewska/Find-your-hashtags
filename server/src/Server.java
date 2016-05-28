import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

/**
 * Created by Zuzia on 2016-05-24.
 */
public class Server {
    private static Long tweetID;
    private static Map<Long, Status> tweetsMap = new TreeMap<Long, Status>();
    private static List<Status> tweets;

    public static void main(String[] args) throws TwitterException {
//        System.out.println("hello world");
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("w6XhZFUdC3YpEMsZJAX1iBzhn")
                .setOAuthConsumerSecret("BJ4dntI5QqosOaQJRtFO9wqNjQz42UVlfg78d7kSwZjRyMHty7")
                .setOAuthAccessToken("734321380800507904-hFH0rmOrtgnRk0dEThCTtqkjqx9L1PT")
                .setOAuthAccessTokenSecret("bIuVPcTLeeFB7awg2S5IJFT8098iiz9957LDTzbQfjNHt");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = tf.getInstance();

        String hashtag = "#BABYMETAL";
        Integer howMany = 10;

//        displayTimelineTweets(twitter);
        searchForHashtag(twitter, hashtag, howMany);
        markAsFavourite(twitter);

    }

    public static void searchForHashtag(twitter4j.Twitter twitter, String hashtag, Integer howMany){
        try {
            if(!hashtag.startsWith("#")){
                hashtag = "#" + hashtag;
                System.out.println(hashtag);
            }

            QueryResult result;
            Query query = new Query(hashtag);
            int i = 0;
            do{
                result = twitter.search(query);
                tweets = result.getTweets();

                for(Status tweet : tweets){
                    tweetsMap.put(tweet.getId(), tweet);
//                    System.out.println(tweet.getUser().getName() + " ----- " + tweet.getText());
                }

                for(Map.Entry entry : tweetsMap.entrySet()) {
                    i++;
                    Status tweet = (Status) entry.getValue();
                    System.out.println(i);
                    System.out.println("@" + tweet.getUser().getName() + " ----- " + tweet.getText());

                    if (howMany != null) {
                        if(i == howMany) break;
                    }
                }
                System.out.println("---------------end of while-------------");
            } while(((query = result.nextQuery()) != null) && i != howMany);
            System.exit(0);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public static void displayTimelineTweets(twitter4j.Twitter twitter){
        List<Status> tweets = null;
        try {
            tweets = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

//        System.out.println("hi");
        for(Status tweet : tweets){
            System.out.println(tweet.getUser().getName() + " ----- " + tweet.getText());
            tweetID = tweet.getId();
            System.out.println("ID: " + tweetID);
        }
    }

    public static void markAsFavourite(twitter4j.Twitter twitter){
        try {
            twitter.createFavorite(tweetID);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
