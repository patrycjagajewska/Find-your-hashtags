package pl.edu.agh.tai.service;

/**
 * Created by Zuzia on 2016-05-31.
 */
public interface IHashtagService {
    void displayTimelineTweets(twitter4j.Twitter twitter);
    void searchForHashtag(twitter4j.Twitter twitter, String hashtag, Integer howMany);
    void markAsFavourite(twitter4j.Twitter twitter, Long tweetID);
}
