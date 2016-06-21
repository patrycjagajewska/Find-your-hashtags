package pl.edu.agh.tai.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import twitter4j.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Zuzia on 2016-06-01.
 */

@SpringBootApplication
public class Application {
    private static Map<Long, Status> tweetsMap = new TreeMap<Long, Status>();
    private static List<Status> tweets;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
