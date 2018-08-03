package Servlet;

import ClassForTest.HttpForTest;
import Db.DatabaseAdapter;
import Json.dbJson.UserList;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SyncUserTest {
    DatabaseAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new DatabaseAdapter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "huangzp");
        map.put("modelnum", "honor7");
        map.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9" +
                ".8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw");
        UserList userList = new UserList();
        Gson gson = new Gson();
        userList.username = "huangzp";
        userList.password = "test for post";
        userList.modified = (new Date()).getTime();
        userList.headimage = "huangzp";
        String content = gson.toJson(userList);
        map.put("content", content);
        String result = HttpForTest.sendPost("http://localhost:8080/Servlet.SyncUser", map);

    }

    @Test
    public void search() throws SQLException, IllegalAccessException {
        Json.dbJson.UserList user = SyncUser.search(adapter, "huangzp");

    }

    @Test
    public void insert() throws SQLException, IllegalAccessException {
        Json.dbJson.UserList user = new Json.dbJson.UserList();
        user.username = "huangzp";
        user.password = "huangzp";
        user.modified = (new Date()).getTime();
        SyncUser.insert(adapter, user);
    }

    @Test
    public void update() throws SQLException, IllegalAccessException, NoSuchFieldException {
        UserList user = SyncUser.search(adapter, "huangzp");
        user.password = "update";
        SyncUser.update(adapter, user);
    }
}