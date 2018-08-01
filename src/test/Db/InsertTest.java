package Db;

import Json.LoginBack;
import Json.Sync;
import Json.dbJson.*;
import Json.dbJson.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
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
        diary.modified = (new Date()).getTime();
        diary.id = 10L;
        diary.text = "just for test";
        diarybook.id = 10L;
        diary.diarybook = diarybook;
        Long now = (new Date()).getTime();

        DatabaseAdapter adapter = new DatabaseAdapter();
        Insert.insert(adapter, diary, "huangzp", now , false);
        Insert.insert(adapter, diary, "huangzp", now , true);
        Insert.insert(adapter, diarybook, "huangzp", now , false);
        Insert.insert(adapter, diarybook, "huangzp", now , true);
        Label label = new Label();
        label.id = 10L;
        label.labelname = "test";
        Insert.insert(adapter, label, "huangzp", now , false);
        Insert.insert(adapter, label, "huangzp", now , true);
        DiaryLabel diaryLabel = new DiaryLabel();
        diaryLabel.id = 10L;
        diaryLabel.diary = diary;
        diaryLabel.label = label;
        Insert.insert(adapter, diaryLabel, "huangzp", now , false);
        Insert.insert(adapter, diaryLabel, "huangzp", now , true);


        Sentence sentence = new Sentence();
        Sentencebook sentencebook = new Sentencebook();
        sentence.status = 9;
        sentence.modified = (new Date()).getTime();
        sentence.id = 10L;
        sentence.text = "just for test";
        sentencebook.id = 10L;
        sentence.sentencebook = sentencebook;

        Insert.insert(adapter, sentence, "huangzp", now , false);
        Insert.insert(adapter, sentence, "huangzp", now , true);
        Insert.insert(adapter, sentencebook, "huangzp", now, false);
        Insert.insert(adapter, sentencebook, "huangzp", now , true);
        SentenceLabel sentenceLabel = new SentenceLabel();
        sentenceLabel.id = 10L;
        sentenceLabel.sentence = sentence;
        sentenceLabel.label = label;
        Insert.insert(adapter, sentenceLabel, "huangzp", now , false);
        Insert.insert(adapter, sentenceLabel, "huangzp", now , true);

    }
}