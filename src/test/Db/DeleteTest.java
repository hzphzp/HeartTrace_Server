package Db;

import Json.dbJson.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeleteTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createSql() {
        //System.out.println(Delete.createSql(Diary.class));
    }

    @Test
    public void delete() {
        boolean flag = false;
        DatabaseAdapter adapter = new DatabaseAdapter();
        flag = Delete.delete(adapter, Diary.class, "huangzp", 2, false);
        flag = Delete.delete(adapter, Diarybook.class, "huangzp", 2, false);
        flag = Delete.delete(adapter, DiaryLabel.class, "huangzp", 2, false);
        flag = Delete.delete(adapter, Label.class, "huangzp", 2, false);
        flag = Delete.delete(adapter, Sentence.class, "huangzp", 2, false);
        flag = Delete.delete(adapter, Sentencebook.class, "huangzp", 2, false);
        flag = Delete.delete(adapter, SentenceLabel.class, "huangzp", 2, false);
    }
    @Test
    public void delete_d() {
        boolean flag = false;
        DatabaseAdapter adapter = new DatabaseAdapter();
        flag = Delete.delete(adapter, Diary.class, "huangzp", 2, true);
        flag = Delete.delete(adapter, Diarybook.class, "huangzp", 2, true);
        flag = Delete.delete(adapter, DiaryLabel.class, "huangzp", 2, true);
        flag = Delete.delete(adapter, Label.class, "huangzp", 2, true);
        flag = Delete.delete(adapter, Sentence.class, "huangzp", 2, true);
        flag = Delete.delete(adapter, Sentencebook.class, "huangzp", 2, true);
        flag = Delete.delete(adapter, SentenceLabel.class, "huangzp", 2, true);
    }
}