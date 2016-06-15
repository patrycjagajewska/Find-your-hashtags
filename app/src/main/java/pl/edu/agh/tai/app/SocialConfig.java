package pl.edu.agh.tai.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import javax.inject.Inject;

/**
 * Created by Zuzia on 2016-06-15.
 */

@Configuration
public class SocialConfig {

    @Inject
    private Environment environment;

    public ConnectionFactoryLocator connectionFactoryLocator(){
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new TwitterConnectionFactory(
                environment.getProperty("oauth.consumerKey"),
                environment.getProperty("oauth.consumerSecret")));

        return registry;
    }
}
