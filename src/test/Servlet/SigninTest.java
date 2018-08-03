package Servlet;

import ClassForTest.HttpForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SigninTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "吴雪晴");
        map.put("password", "huangzp");
        String result = HttpForTest.sendPost("http://localhost:8080/Servlet.Signin", map);
    }
}