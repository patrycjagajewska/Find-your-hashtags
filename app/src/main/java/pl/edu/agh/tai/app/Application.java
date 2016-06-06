package pl.edu.agh.tai.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Zuzia on 2016-06-01.
 */

@SpringBootApplication
public class Application {
    private static Map<Long, Status> tweetsMap = new TreeMap<Long, Status>();
    private static List<Status> tweets;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    public static void main(String[] args) throws TwitterException {
////        System.out.println("hello world");
//
//        TwitterFactory tf = new TwitterFactory();
//        twitter4j.Twitter twitter = tf.getInstance();
//
//        String hashtag = "#BABYMETAL";
//        Integer howMany = 5;
//
////        displayTimelineTweets(twitter);
//        searchForHashtag(twitter, hashtag, howMany);
//
//    }

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
//                    System.out.println("@" + tweet.getUser().getName() + " ----- " + tweet.getText());

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
}
