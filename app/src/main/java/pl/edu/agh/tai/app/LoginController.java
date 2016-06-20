package pl.edu.agh.tai.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zuzia on 2016-06-20.
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private OAuthToken authToken;
}
