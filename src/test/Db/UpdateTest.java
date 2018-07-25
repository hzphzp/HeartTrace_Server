package Db;

import Json.dbJson.*;
import javafx.scene.chart.PieChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        DatabaseAdapter adapter = new DatabaseAdapter();
        boolean flag = false;
        Diary diary = Search.search(adapter, Diary.class, "huangzp", 1, false);
        diary.text = "update";
        flag = Update.update(adapter, diary, "huangzp", false);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 100, false);
        diarybook.diarybookName = "update";
        flag = Update.update(adapter, diarybook, "huangzp", false);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 1, false);
        diaryLabel.status = 1;
        flag = Update.update(adapter, diaryLabel, "huangzp", false);

        Label label = Search.search(adapter, Label.class, "huangzp", 1, false);
        label.labelname = "update";
        flag = Update.update(adapter, label, "huangzp", false);
        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 1, false);
        sentence.text = "update";
        flag = Update.update(adapter, sentence, "huangzp", false);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 100, false);
        sentencebook.sentencebookName = "update";
        flag = Update.update(adapter, sentencebook, "huangzp", false);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 1, false);
        sentenceLabel.status = 1;
        flag = Update.update(adapter, sentenceLabel, "huangzp", false);

    }

    @Test
    public void update_delete() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        boolean flag = true;
        Diary diary = Search.search(adapter, Diary.class, "huangzp", 1, true);
        diary.text = "update";
        flag = Update.update(adapter, diary, "huangzp", true);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 100, true);
        diarybook.diarybookName = "update";
        flag = Update.update(adapter, diarybook, "huangzp", true);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 1, true);
        diaryLabel.status = 1;
        flag = Update.update(adapter, diaryLabel, "huangzp", true);

        Label label = Search.search(adapter, Label.class, "huangzp", 1, true);
        label.labelname = "update";
        flag = Update.update(adapter, label, "huangzp", true);

        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 1, true);
        sentence.text = "update";
        flag = Update.update(adapter, sentence, "huangzp", true);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 100, true);
        sentencebook.sentencebookName = "update";
        flag = Update.update(adapter, sentencebook, "huangzp", true);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 1, true);
        sentenceLabel.status = 1;
        flag = Update.update(adapter, sentenceLabel, "huangzp", true);

    }
}