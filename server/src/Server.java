import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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

        List<Status> status = twitter.getHomeTimeline();

        System.out.println("hi");
        for(Status st : status){
            System.out.println(st.getUser().getName() + " ----- " + st.getText());
        }
    }
}
