import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

/**
 * Created by Zuzia on 2016-05-24.
 */
public class Server {
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
        Integer howMany = 5;

//        displayTimelineTweets(twitter);
        searchForHashtag(twitter, hashtag, howMany);

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
                }

                for(Map.Entry entry : tweetsMap.entrySet()) {
                    i++;
                    Status tweet = (Status) entry.getValue();
                    Long tweetID = (Long) entry.getKey();
                    System.out.println(i);
                    System.out.println(tweetID);
                    System.out.println("@" + tweet.getUser().getName() + " ----- " + tweet.getText());

                    markAsFavourite(twitter, tweetID);

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
        try {
            tweets = twitter.getHomeTimeline();

            for(Status tweet : tweets){
                System.out.println(tweet.getUser().getName() + " ----- " + tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public static void markAsFavourite(twitter4j.Twitter twitter, Long tweetID){
        try {
            twitter.createFavorite(tweetID);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
