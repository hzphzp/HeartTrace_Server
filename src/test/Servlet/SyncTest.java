package Servlet;

import ClassForTest.GenJson;
import ClassForTest.HttpForTest;
import Db.*;
import Json.dbJson.*;
import com.google.gson.Gson;
import javafx.scene.chart.PieChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.security.UnresolvedPermission;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SyncTest {

    @Before
    public void setUp() throws Exception {
        String Url = "http://localhost:8080/Servlet.Sync";
        Map<String, String > map = new HashMap<String, String>();
        map.put("username", "huangzp");
        map.put("modelnum", "honor7");
        map.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9.8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw");
        map.put("content", GenJson.fromDefault());
        //HttpForTest.sendPost(Url, map);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() {
        Json.Sync sync = new Json.Sync();
        DatabaseAdapter adapter = new DatabaseAdapter();
        Diary diary = new Diary();
        diary.status = 9;
        diary.anchor = (new Date()).getTime();
        diary.id = 8;
        diary.text = "test for post";
        Diarybook diarybook = new Diarybook();
        diarybook.status = 9;
        diarybook.anchor = (new Date()).getTime();
        diarybook.diarybookName = "test for post";
        diarybook.id = 8;
        diary.diarybook = diarybook;
        diary.status = 0;
        diarybook.status = 0;
        sync.DiaryList.add(diary);
        sync.DiarybookList.add(diarybook);
        Gson gson = new Gson();
        String content = gson.toJson(sync);
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "huangzp");
        map.put("modelnum", "honor7");
        map.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9" +
                ".8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw");
        map.put("content", content);
        String result;
        //String result = HttpForTest.sendPost("http://localhost:8080/Servlet.Sync", map);
        diary.text = "update";
        diarybook.diarybookName = "update";
        diary.anchor = 1532576482991L;
        diary.status = 1;
        diarybook.anchor = 1532576483099L;
        diarybook.status = 1;
        sync.DiaryList.set(0, diary);
        sync.DiarybookList.set(0, diarybook);
        String content1 = gson.toJson(sync);
        boolean b = map.replace("content", content, content1);
        //result = HttpForTest.sendPost("http://localhost:8080/Servlet.Sync", map);
        diary.anchor = 1532577901849L;
        diarybook.anchor = 1532577901973L;
        diary.status = -1;
        diarybook.status = -1;
        sync.DiaryList.set(0, diary);
        sync.DiarybookList.set(0, diarybook);
        String content2 = gson.toJson(sync);
        b = map.replace("content", content1, content2);
        result = HttpForTest.sendPost("http://localhost:8080/Servlet.Sync", map);

    }

    @Test
    public void doGet() {
        System.out.println(HttpForTest.sendGet("http://localhost:8080/Servlet.Sync?username=huangzp" +
                "&modelnum=honor7" +
                "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9." +
                "8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw" +
                "&maxanchor=-1"));


    }
}