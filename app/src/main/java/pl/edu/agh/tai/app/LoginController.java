package pl.edu.agh.tai.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Zuzia on 2016-06-20.
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private OAuthToken oauthToken;

    private TwitterFactory factory = new TwitterFactory();
    private Twitter twitter = factory.getInstance();

    private MyAccessToken accessToken;

    public String printWelcome(HttpServletResponse response, HttpServletRequest request){
        twitter.setOAuthConsumer(oauthToken.getConsumerKey(), oauthToken.getConsumerSecret());
        RequestToken requestToken;

        String callbackURL = "http://127.0.0.1:8080/#/";
        try {
            requestToken = twitter.getOAuthRequestToken(callbackURL);
            String token = requestToken.getToken();
            String tokenSecret = requestToken.getTokenSecret();

            accessToken.setTokensecret(tokenSecret);
            accessToken.setToken(token);

            String authUrl = requestToken.getAuthorizationURL();
            request.setAttribute("authUrl", authUrl);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return "login";
    }
}
