package Db;

import Json.dbJson.*;
import javafx.scene.chart.PieChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SearchTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createSql() {
        //System.out.println(Search.createSql(Sentence.class));
    }

    @Test
    public void search() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        Diary diary = Search.search(adapter, Diary.class,"huangzp", 1, false);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 100, false);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 1, false);
        Label label = Search.search(adapter, Label.class, "huangzp", 1, false);
        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 1, false);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 100, false);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 1, false);
    }
    @Test
    public void search_delete() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        Diary diary = Search.search(adapter, Diary.class,"huangzp", 1, true);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 100, true);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 1, true);
        Label label = Search.search(adapter, Label.class, "huangzp", 1, true);
        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 1, true);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 100, true);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 1, true);
    }

    @Test
    public void createSqlForGet() {
       //System.out.println(Search.createSqlForGet(Diary.class));
    }

    @Test
    public void searchForGet() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        List<Diary> diaryList = Search.searchForGet(adapter, Diary.class, -1L);
        List<Diarybook> diarybookList = Search.searchForGet(adapter, Diarybook.class, -1L);
        List<DiaryLabel> diaryLabelList = Search.searchForGet(adapter, DiaryLabel.class, -1L);
        List<Label> labelList = Search.searchForGet(adapter, Label.class, -1L);
        List<Sentence> sentenceList = Search.searchForGet(adapter, Sentence.class, -1L);
        List<Sentencebook> sentencebookList = Search.searchForGet(adapter, Sentencebook.class, -1L);
        List<SentenceLabel> sentenceLabelList = Search.searchForGet(adapter, SentenceLabel.class, -1L);

    }
}