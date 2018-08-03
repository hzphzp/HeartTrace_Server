package Servlet;
import ClassForTest.GenJson;
import ClassForTest.HttpForTest;
import Db.*;
import Json.dbJson.*;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
        //String content = "{DiaryList':[{'date':1533181996620,'id':1024865691615064064,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533181996621,'status':0,'text':'咩','textAlignment':0,'textSize':20.0}],DiarybookList':[],DiaryLabelList':[],LabelList':[],SentenceList':[],SentencebookList':[],SentenceLabelList':[]}";
        String content = "{'DiaryList':[],'DiarybookList':[],'DiaryLabelList':[{'diary':{'id':1025006607365197824,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':1,'lineSpacingMultiplier':0,'modified':0,'status':0,'textAlignment':0,'textSize':20.0},'id':1025006780589981696,'label':{'id':1025006780480929792,'modified':0,'status':0},'modified':1533215634854,'status':0}],'LabelList':[],'SentenceList':[],'SentencebookList':[],'SentenceLabelList':[]}";
        Diary diary = new Diary();
        diary.status = 0;
        diary.id = 20;
        diary.text = "huangzp";
        diary.modified = (new Date()).getTime();
        Diarybook diarybook = new Diarybook();
        diarybook.id = 20;
        diary.diarybook = diarybook;
        sync.DiaryList.add(diary);
        sync.DiarybookList.add(diarybook);
        Gson gson = new Gson();
        //String content = gson.toJson(sync);
        Map<String, String> map = new HashMap<>();
        map.put("username", "huangzp");
        map.put("modelnum", "honor7");
        map.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9" +
                ".8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw");
        map.put("content", content);
        map.put("anchor", "1533227264763");
        String result;
        //result = HttpForTest.sendPost("http://122.152.195.134:8080/HeartTrace_Server_war/Servlet.Sync1", map);
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