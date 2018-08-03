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
    public void search_anchor() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        long result;
        result = Search.search_anchor(adapter, Diary.class, "huangzp", 10L, false);
        result = Search.search_anchor(adapter, Diarybook.class, "huangzp", 10L, false);
        result = Search.search_anchor(adapter, DiaryLabel.class, "huangzp", 10L, false);
        result = Search.search_anchor(adapter, Label.class, "huangzp", 10L, false);
        result = Search.search_anchor(adapter, Sentence.class, "huangzp", 10L, false);
        result = Search.search_anchor(adapter, Sentencebook.class, "huangzp", 10L, false);
        result = Search.search_anchor(adapter, SentenceLabel.class, "huangzp", 10L, false);
    }

    @Test
    public void search_delete() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        long result;
        result = Search.search_anchor(adapter, Diary.class, "huangzp", 10L, true);
        result = Search.search_anchor(adapter, Diarybook.class, "huangzp", 10L, true);
        result = Search.search_anchor(adapter, DiaryLabel.class, "huangzp", 10L, true);
        result = Search.search_anchor(adapter, Label.class, "huangzp", 10L, true);
        result = Search.search_anchor(adapter, Sentence.class, "huangzp", 10L, true);
        result = Search.search_anchor(adapter, Sentencebook.class, "huangzp", 10L, true);
        result = Search.search_anchor(adapter, SentenceLabel.class, "huangzp", 10L, true);
    }

    @Test
    public void createSqlForGet() {
        //System.out.println(Search.createSqlForGet(Diary.class));
    }

    @Test
    public void searchForGet() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        List<Diary> diaryList = Search.searchWithAnchor(adapter, Diary.class, "huangzp", -1L);
        List<Diarybook> diarybookList = Search.searchWithAnchor(adapter, Diarybook.class, "huangzp", -1L);
        List<DiaryLabel> diaryLabelList = Search.searchWithAnchor(adapter, DiaryLabel.class, "huangzp", -1L);
        List<Label> labelList = Search.searchWithAnchor(adapter, Label.class, "huangzp", -1L);
        List<Sentence> sentenceList = Search.searchWithAnchor(adapter, Sentence.class, "huangzp", -1L);
        List<Sentencebook> sentencebookList = Search.searchWithAnchor(adapter, Sentencebook.class, "huangzp", -1L);
        List<SentenceLabel> sentenceLabelList = Search.searchWithAnchor(adapter, SentenceLabel.class, "huangzp", -1L);

    }

    @Test
    public void search() {
        DatabaseAdapter adapter = new DatabaseAdapter();
        Diary diary = Search.search(adapter, Diary.class, "huangzp", 10L, false);
        Diarybook diarybook = Search.search(adapter, Diarybook.class, "huangzp", 10L, false);
        DiaryLabel diaryLabel = Search.search(adapter, DiaryLabel.class, "huangzp", 10L, false);
        Label label = Search.search(adapter, Label.class, "huangzp", 10L, false);
        Sentence sentence = Search.search(adapter, Sentence.class, "huangzp", 10L, false);
        Sentencebook sentencebook = Search.search(adapter, Sentencebook.class, "huangzp", 10L, false);
        SentenceLabel sentenceLabel = Search.search(adapter, SentenceLabel.class, "huangzp", 10L, false);
    }




}