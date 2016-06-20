package pl.edu.agh.tai.app;

/**
 * Created by Zuzia on 2016-06-20.
 */
public class MyAccessToken {

    private String token;
    private String tokensecret;
    public String getTokensecret() {
        return tokensecret;
    }
    public void setTokensecret(String tokensecret) {
        this.tokensecret = tokensecret;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
