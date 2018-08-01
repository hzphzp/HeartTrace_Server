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
        diary.modified = (new Date()).getTime();
        diary.id = 17L;
        diary.text = "test for post";
        Diarybook diarybook = new Diarybook();
        diarybook.status = 9;
        diarybook.modified = (new Date()).getTime();
        diarybook.diarybookName = "test for post";
        diarybook.id = 17L;
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
        map.put("anchor", "-1");
        String result;
        //result = HttpForTest.sendPost("http://localhost:8080/Servlet.Sync1", map);
        diary.text = "update";
        diarybook.diarybookName = "update";
        diary.modified = 1532576482991L;
        diary.status = 1;
        diarybook.modified = 1532576483099L;
        diarybook.status = 1;
        sync.DiaryList.set(0, diary);
        sync.DiarybookList.set(0, diarybook);
        String content1 = gson.toJson(sync);
        boolean b = map.replace("content", content, content1);
        //result = HttpForTest.sendPost("http://localhost:8080/Servlet.Sync1", map);
        diary.modified =     2000000000000L;
        diarybook.modified = 2000000000000L;
        diary.status = -1;
        diarybook.status = -1;
        sync.DiaryList.set(0, diary);
        sync.DiarybookList.set(0, diarybook);
        String content2 = gson.toJson(sync);
        b = map.replace("content", content1, content2);
        result = HttpForTest.sendPost("http://localhost:8080/Servlet.Sync1", map);

    }

    @Test
    public void doGet() {
        System.out.println(HttpForTest.sendGet("http://localhost:8080/Servlet.Sync?username=huangzp" +
                "&modelnum=honor7" +
                "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9." +
                "8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw" +
                "&maxmodified=-1"));


    }
}