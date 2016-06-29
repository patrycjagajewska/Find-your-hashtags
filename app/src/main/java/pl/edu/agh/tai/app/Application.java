package pl.edu.agh.tai.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
