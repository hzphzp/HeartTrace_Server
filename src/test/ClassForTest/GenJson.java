package ClassForTest;

import Json.Sync;
import Json.dbJson.Diary;
import Json.dbJson.Diarybook;
import com.google.gson.Gson;

import java.util.Date;

public class GenJson {
    public static String fromDefault() {
        Sync sync = new Sync();
        Diary diary = new Diary();
        diary.status = 0;
        diary.anchor = (long) 7;
        diary.id = 1;
        diary.text = "huangzp is the best";
        diary.date = new Date().getTime();
        Diarybook diarybook = new Diarybook();
        diarybook.status = 0;
        diarybook.anchor = 1;
        diarybook.id = 2;
        diarybook.diarybookName = "happy";
        diary.diarybook = diarybook;
        sync.DiaryList.add(diary);
        sync.DiarybookList.add(diarybook);
        Gson gson = new Gson();
        String json = gson.toJson(sync);
        return json;
    }
}
