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
        //String content = "{'DiaryList':[],'DiarybookList':[],'DiaryLabelList':[{'diary':{'id':1025006607365197824,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':1,'lineSpacingMultiplier':0,'modified':0,'status':0,'textAlignment':0,'textSize':20.0},'id':1025006780589981696,'label':{'id':1025006780480929792,'modified':0,'status':0},'modified':1533215634854,'status':0}],'LabelList':[],'SentenceList':[],'SentencebookList':[],'SentenceLabelList':[]}";
        //String content = "{'DiaryList':[{'date':1533395499696,'diarybook':{},'htmlText':'<p dir=\'ltr\'><img src=\'null\'><br>\n</p>\n','id':1025761188420538368,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533395730370,'status':-1,'text':'/storage/emulated/0/HeartTrace/pic/1a7d7ad8fb105d4adb1ee44d84baecd1\n','textAlignment':0,'textSize':20.0},{'date':1533395691408,'htmlText':'','id':1025761992514777088,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533395734257,'status':-1,'text':'','textAlignment':0,'textSize':20.0},{'date':1533395720153,'htmlText':'<p dir=\'ltr\'><img src=\'null\'><br>\n</p>\n','id':1025762113080000512,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533395736572,'status':-1,'text':'/storage/emulated/0/HeartTrace/pic/1a7d7ad8fb105d4adb1ee44d84baecd1\n','textAlignment':0,'textSize':20.0,'diarybook':{}}],'DiarybookList':[],'DiaryLabelList':[],'LabelList':[],'SentenceList':[],'SentencebookList':[],'SentenceLabelList':[]}";
        //String content = "{'DiaryList':[{'date':1533393527657,'diarybook':{'id':0,'modified':0,'status':0},'htmlText':'<p dir=\'ltr\'><tt><span style=\'color:#000000;\'>&#32763;&#36710;&#20102;&#40493;</span></tt><img src=\'null\'><br>\n</p>\n','id':1025752917085347840,'islike':false,'letterSpacing':0.0,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533398989262,'status':-1,'text':'翻车了鸭/storage/emulated/0/HeartTrace/pic/img_1025752882239082496.jpg\n','textAlignment':0,'textSize':20.0},{'date':1533398994473,'htmlText':'<p dir=\'ltr\'><span style=\'color:#000000;\'><tt>j</tt></span><span style=\'color:#000000;\'><tt>h</tt></span><span style=\'color:#000000;\'><tt>d</tt></span><span style=\'color:#000000;\'><tt>a</tt></span><span style=\'color:#000000;\'><tt>d</tt></span><span style=\'color:#000000;\'><tt>j</tt></span><span style=\'color:#000000;\'><tt>b</tt></span><span style=\'color:#000000;\'><tt>x</tt></span><span style=\'color:#000000;\'><tt>x</tt></span></p>\n','id':1025775846577717248,'islike':false,'letterSpacing':0.2,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533398994474,'status':0,'text':'jhdadjbxx','textAlignment':0,'textSize':20.0}],'DiarybookList':[],'DiaryLabelList':[],'LabelList':[],'SentenceList':[],'SentencebookList':[],'SentenceLabelList':[]}";
        //String content = "{DiaryList':[{'date':1533181996620,'htmlText':'<p dir=\'ltr\'><span style=\'color:#000000;\'><tt>&#21673;</tt></span></p>\n','id':1024865691615064064,'isLike':false,'letterSpacing':0.2,'lineSpacingExtra':0,'lineSpacingMultiplier':1,'modified':1533181996621,'status':0,'text':'咩','textAlignment':0,'textSize':20.0}],DiarybookList':[],DiaryLabelList':[],LabelList':[],SentenceList':[],SentencebookList':[],SentenceLabelList':[]}";
        //String content = "{\"DiaryList\":[{\"alignmentType\":3,\"background\":0,\"date\":1533745970818,\"fontType\":2,\"htmlText\":\"\",\"id\":1027231170842329088,\"islike\":false,\"letterSpacing\":0.2,\"lineSpacingExtra\":0,\"lineSpacingMultiplier\":1,\"modified\":1533745971118,\"status\":0,\"text\":\"早上好\",\"textAlignment\":0,\"textSize\":20.0}],\"DiarybookList\":[],\"DiaryLabelList\":[],\"LabelList\":[],\"SentenceList\":[],\"SentencebookList\":[],\"SentenceLabelList\":[]}";
        String content = "{\"DiaryList\":[{\"alignmentType\":3,\"background\":0,\"date\":1533789315986,\"fontType\":3,\"htmlText\":\"<p dir=\\\"ltr\\\"><span style=\\\"color:#000000;\\\">&#21673;</span><span style=\\\"color:#000000;\\\">&#21673;</span><span style=\\\"color:#FF8C00;\\\">&#21673;&#21673;&#21673;</span><span style=\\\"color:#006400;\\\">&#21673;</span></p>\\n\",\"id\":1027412973653975040,\"islike\":false,\"letterSpacing\":0.2,\"lineSpacingExtra\":0,\"lineSpacingMultiplier\":1,\"modified\":1533789360961,\"status\":0,\"text\":\"咩咩咩咩咩咩\",\"textAlignment\":0,\"textSize\":20.0}],\"DiarybookList\":[],\"DiaryLabelList\":[],\"LabelList\":[],\"SentenceList\":[],\"SentencebookList\":[],\"SentenceLabelList\":[]}";
        String content1 = "{\"DiaryList\":[{\"alignmentType\":3,\"background\":0,\"date\":1533789315986,\"fontType\":3,\"htmlText\":\"<p dir=\\\"ltr\\\"><span style=\\\"color:#000000;\\\">&#21673;</span><span style=\\\"color:#000000;\\\">&#21673;</span><span style=\\\"color:#FF8C00;\\\">&#21673;&#21673;&#21673;</span><span style=\\\"color:#006400;\\\">&#21673;</span></p>\\n\",\"id\":1027412973653975040,\"islike\":false,\"letterSpacing\":0.2,\"lineSpacingExtra\":0,\"lineSpacingMultiplier\":1,\"modified\":1533789360961,\"status\":0,\"text\":\"咩咩咩咩咩咩\",\"textAlignment\":0,\"textSize\":20.0}],\"DiarybookList\":[],\"DiaryLabelList\":[],\"LabelList\":[],\"SentenceList\":[],\"SentencebookList\":[],\"SentenceLabelList\":[]}";
        boolean f = content.equals(content1);

        Diary diary = new Diary();
        diary.status = 0;
        diary.id = 20;
        diary.text = "huangzp";
        diary.htmlText = "<p dir=\'ltr\'><img src=\'null\'><br>\n</p>\n";
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