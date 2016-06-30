package pl.edu.agh.tai.app.token;

public class OAuthToken {

    private String consumerKey;
    private String consumerSecret;

    public void setConsumerKey(String consumerKey) {this.consumerKey = consumerKey;}

    public String getConsumerKey() { return consumerKey; }

    public void setConsumerSecret(String consumerSecret) {this.consumerSecret = consumerSecret;}

    public String getConsumerSecret() { return consumerSecret; }
}
