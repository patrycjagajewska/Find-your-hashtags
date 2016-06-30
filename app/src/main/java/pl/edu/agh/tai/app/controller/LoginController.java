package pl.edu.agh.tai.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.tai.app.token.CustomAccessToken;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/twitter")
public class LoginController {

    @Autowired
    private TwitterFactory twitterFactory;

    @Autowired
    private CustomAccessToken accessToken;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        Twitter twitter = twitterFactory.getInstance();

        RequestToken requestToken;
        try {
            String callbackURL = "http://127.0.0.1:8080/twitter/callback";
            System.out.println("callback url is " + callbackURL);
            requestToken = twitter.getOAuthRequestToken(callbackURL);
            String token = requestToken.getToken();
            String tokenSecret = requestToken.getTokenSecret();
            accessToken.setTokenSecret(tokenSecret);
            accessToken.setToken(token);
            String authUrl = requestToken.getAuthorizationURL();
            model.addAttribute("authUrl", authUrl);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return "login";
    }

    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleRequestInternal(HttpServletRequest request) throws Exception {
        Twitter twitter = twitterFactory.getInstance();

        String verifier = request.getParameter("oauth_verifier");
        RequestToken requestToken = new RequestToken(accessToken.getToken(), accessToken.getTokenSecret());
        twitter4j.auth.AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
        twitter.setOAuthAccessToken(accessToken);

        this.accessToken.setTwitterAccessToken(accessToken);

        User user = twitter.verifyCredentials();
        System.out.println(user.getName());

        return "redirect:/#/";
    }
}