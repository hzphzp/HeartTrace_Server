package Db;

import Json.LoginBack;
import Json.Sync;
import Json.dbJson.*;
import Json.dbJson.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Date;

import static org.junit.Assert.*;

public class InsertTest {

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert() {
        Diary diary = new Diary();
        Diarybook diarybook = new Diarybook();
        diary.status = 9;
        diary.anchor = (new Date()).getTime();
        diary.id = 2;
        diary.text = "just for test";
        diarybook.id = 2;
        diary.diarybook = diarybook;

        DatabaseAdapter adapter = new DatabaseAdapter();
        Insert.insert(adapter, diary, "huangzp", false);
        Insert.insert(adapter, diary, "huangzp", true);
        Insert.insert(adapter, diarybook, "huangzp", false);
        Insert.insert(adapter, diarybook, "huangzp", true);
        Label label = new Label();
        label.id = 2;
        label.labelname = "test";
        Insert.insert(adapter, label, "huangzp", false);
        Insert.insert(adapter, label, "huangzp", true);
        DiaryLabel diaryLabel = new DiaryLabel();
        diaryLabel.id = 2;
        diaryLabel.diary = diary;
        diaryLabel.label = label;
        Insert.insert(adapter, diaryLabel, "huangzp", false);
        Insert.insert(adapter, diaryLabel, "huangzp", true);


        Sentence sentence = new Sentence();
        Sentencebook sentencebook = new Sentencebook();
        sentence.status = 9;
        sentence.anchor = (new Date()).getTime();
        sentence.id = 2;
        sentence.text = "just for test";
        sentencebook.id = 2;
        sentence.sentencebook = sentencebook;

        Insert.insert(adapter, sentence, "huangzp", false);
        Insert.insert(adapter, sentence, "huangzp", true);
        Insert.insert(adapter, sentencebook, "huangzp", false);
        Insert.insert(adapter, sentencebook, "huangzp", true);
        SentenceLabel sentenceLabel = new SentenceLabel();
        sentenceLabel.id = 2;
        sentenceLabel.sentence = sentence;
        sentenceLabel.label = label;
        Insert.insert(adapter, sentenceLabel, "huangzp", false);
        Insert.insert(adapter, sentenceLabel, "huangzp", true);

    }
}