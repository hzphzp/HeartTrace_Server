package Json;

import Json.dbJson.Diary;
import Json.dbJson.Diarybook;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class SyncTest {
    private Sync sync = null;
    @Before
    public void setUp() throws Exception {
        sync = new Sync();
    }

    @After
    public String tearDown() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(sync);
        return json;
    }

    @Test
    public void generateJson() throws Exception{
        Diary diary = new Diary();
        diary.status = 0;
        diary.modified = (long)7;
        diary.id = 1L;
        diary.text = "huangzp is the best";
        diary.date = new Date().getTime();
        Diarybook diarybook = new Diarybook();
        diarybook.status = 0;
        diarybook.modified = 1;
        diarybook.id = 2L;
        diarybook.diarybookName = "happy";
        diary.diarybook = diarybook;
        sync.DiaryList.add(diary);
        sync.DiarybookList.add(diarybook);

    }
}