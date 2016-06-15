package pl.edu.agh.tai.app;

import twitter4j.Status;

public class TweetStatus {
    public String id;
    public Status tweet;

    public TweetStatus(String id, Status tweet) {
        this.id = id;
        this.tweet = tweet;
    }
}
