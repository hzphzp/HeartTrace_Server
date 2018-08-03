package Jwt;

import com.auth0.jwt.JWTVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JavaWebTokenTest {
    private String username;
    private String modelnum;
    private String token;
    @Before
    public void setUp() throws Exception {
        username = "wuxueqing";
        modelnum = "M688C";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createToken() {
        //token = JavaWebToken.createToken(username, modelnum);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3dXh1ZXFpbmciLCJzdWIiOiJNNjg4QyIsIm5iZiI6MTUzMzEyODAyNCwiaXNzIjoiaHVhbmd6cCIsImV4cCI6MTUzMzIxNDQyMywiaWF0IjoxNTMzMTI4MDIzfQ.AWKDtVhtL5uXu6c_l2C4JazebPrYJmUgZskmrPEhkA8";
        System.out.println(token);
    }

    @Test
    public void verifyToken() {
        createToken();
        JavaWebToken.verifyToken(token, username, modelnum);
    }
}