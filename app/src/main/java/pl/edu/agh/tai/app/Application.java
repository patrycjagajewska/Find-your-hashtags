package pl.edu.agh.tai.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import pl.edu.agh.tai.app.token.CustomAccessToken;
import pl.edu.agh.tai.app.token.OAuthToken;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Status;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Zuzia on 2016-06-01.
 */
@ImportResource("classpath:config.xml")
@SpringBootApplication
public class Application {

    @Autowired
    private OAuthToken oAuthToken;

    @Autowired
    private CustomAccessToken customAccessToken;

    @Autowired
    private TwitterFactory twitterFactory;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public TwitterFactory twitterFactory() {
        Configuration configuration = new ConfigurationBuilder()
                .setOAuthConsumerKey(oAuthToken.getConsumerKey())
                .setOAuthConsumerSecret(oAuthToken.getConsumerSecret())
                .setOAuthAccessToken(customAccessToken.getToken())
                .setOAuthAccessTokenSecret(customAccessToken.getTokenSecret())
                .build();

        return new TwitterFactory(configuration);
    }
}
