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
        username = "huangzp";
        modelnum = "honor7";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createToken() {
        token = JavaWebToken.createToken(username, modelnum);
        System.out.println(token);
    }

    @Test
    public void verifyToken() {
        createToken();
        JavaWebToken.verifyToken(token, username, modelnum);
    }
}