import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Created by Zuzia on 2016-05-24.
 */
public class Server {
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

        String hashtag = "BABYMETAL";
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
            String howManyString;
            do{
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();

                for (Status tweet : tweets) {
                    System.out.println(tweet.getUser().getName() + " ----- " + tweet.getText() + "\n");
                    if(howMany != null){
                        howMany--;
                        System.out.println("howMany = " + howMany.toString());
                        if(howMany == 0) break;
                    }
                }
                System.out.println("---------------end of while-------------");
            } while(((query = result.nextQuery()) != null) && howMany != 0);
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
        }
    }
}
