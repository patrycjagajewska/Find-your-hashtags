package pl.edu.agh.tai.app.token;

import twitter4j.auth.AccessToken;

public class CustomAccessToken {

    private String token;
    private String tokenSecret;

    private AccessToken twitterAccessToken;

    public AccessToken getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(AccessToken twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
