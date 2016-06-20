package pl.edu.agh.tai.app;

/**
 * Created by Zuzia on 2016-06-20.
 */
public class OAuthToken {

    public String consumerKey;
    public String consumerSecret;
    public String getConsumerKey() {
        return consumerKey;
    }
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }
    public String getConsumerSecret() {
        return consumerSecret;
    }
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }
}
