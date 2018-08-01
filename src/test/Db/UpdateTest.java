package Db;

import Json.dbJson.*;
import javafx.scene.chart.PieChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class UpdateTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createSql() {
        //System.out.println(Update.createSql(Diary.class));
    }

    @Test
    public void update() {
        long now = (new Date()).getTime();
        DatabaseAdapter adapter = new DatabaseAdapter();
        boolean flag = false;
        Diary diary = Search.search(adapter, Diary.class, "huangzp", 10L, false);
        diary.text = "update";
        flag = Update.update(adapter, diary, "huangzp", now, false);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 10L, false);
        diarybook.diarybookName = "update";
        flag = Update.update(adapter, diarybook, "huangzp", now, false);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 10L, false);
        diaryLabel.status = 1;
        flag = Update.update(adapter, diaryLabel, "huangzp", now, false);

        Label label = Search.search(adapter, Label.class, "huangzp", 10L, false);
        label.labelname = "update";
        flag = Update.update(adapter, label, "huangzp", now, false);
        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 10L, false);
        sentence.text = "update";
        flag = Update.update(adapter, sentence, "huangzp", now, false);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 10L, false);
        sentencebook.sentencebookName = "update";
        flag = Update.update(adapter, sentencebook, "huangzp", now, false);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 10L, false);
        sentenceLabel.status = 1;
        flag = Update.update(adapter, sentenceLabel, "huangzp", now, false);

    }

    @Test
    public void update_delete() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        boolean flag = true;
        long now = (new Date()).getTime();
        Diary diary = Search.search(adapter, Diary.class, "huangzp", 10L, true);
        diary.text = "update";
        flag = Update.update(adapter, diary, "huangzp", now, true);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 10L, true);
        diarybook.diarybookName = "update";
        flag = Update.update(adapter, diarybook, "huangzp", now, true);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 10L, true);
        diaryLabel.status = 1;
        flag = Update.update(adapter, diaryLabel, "huangzp", now , true);

        Label label = Search.search(adapter, Label.class, "huangzp", 10L, true);
        label.labelname = "update";
        flag = Update.update(adapter, label, "huangzp", now, true);

        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 10L, true);
        sentence.text = "update";
        flag = Update.update(adapter, sentence, "huangzp", now, true);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 10L, true);
        sentencebook.sentencebookName = "update";
        flag = Update.update(adapter, sentencebook, "huangzp", now, true);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 10L, true);
        sentenceLabel.status = 1;
        flag = Update.update(adapter, sentenceLabel, "huangzp", now, true);

    }
}