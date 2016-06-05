package pl.edu.agh.tai.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import twitter4j.TwitterException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TweetException extends RuntimeException{
    public TweetException(TwitterException e) {
        super(e.getMessage());
    }
}
