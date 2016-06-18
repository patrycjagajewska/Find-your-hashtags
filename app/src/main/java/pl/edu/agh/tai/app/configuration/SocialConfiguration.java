package pl.edu.agh.tai.app.configuration;

//import twitter.services.ConnectionSignUpService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

/**
 * Created by Zuzia on 2016-06-17.
 */

@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {

    private DataSource dataSource;
//    private UsersDao usersDao;

    //Callback method to allow configuration of ConnectionFactorys.
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory( new TwitterConnectionFactory(environment.getProperty("twitter.consumerKey"),
                environment.getProperty("facebook.consumerSecret")));
    }

    //Callback method to enable creation of a UserIdSource that uniquely identifies the current user.
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    //Callback method to create an instance of UsersConnectionRepository.
    // Will be used to create a request-scoped instance of ConnectionRepository for the current user.
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setConnectionSignUp(new ConnectionSignUpService()});
        return repository;
    }
}
