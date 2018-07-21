package Json;

import Json.dbJson.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqltoJson {
    public static Diary toDiary(ResultSet rs) {
        //status默认设置成为9
        Diary diary = new Diary();
        try {
            diary.anchor = rs.getLong("anchor");
            diary.status = 9;
            diary.id = rs.getInt("id");
            diary.diarybook.id = rs.getInt("diarybook");
            diary.htmlText = rs.getString("htmlText");
            diary.text = rs.getString("text");
            diary.date = rs.getLong("date");
            diary.islike = rs.getBoolean("islike");
            diary.letterSpacing = rs.getFloat("letterSpacing");
            diary.lineSpacingMultiplier = rs.getInt("lineSpacingMultiplier");
            diary.lineSpacingExtra = rs.getInt("lineSpacingExtra");
            diary.textSize = rs.getFloat("textSize");
            diary.textAlignment = rs.getInt("textAlignment");
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return diary;
        }
    }
    public static Diarybook toDiarybook(ResultSet rs){
        //status默认设置为9
        Diarybook diarybook = new Diarybook();
        try{
            diarybook.status = 9;
            diarybook.anchor = rs.getLong("anchor");
            diarybook.id = rs.getInt("id");
            diarybook.diarybookName = rs.getString("diarybookName");
            diarybook.description = rs.getString("description");
        }catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return diarybook;
        }
    }
    public static DiaryLabel toDiaryLabel(ResultSet rs){
        //status默认设置为9
        DiaryLabel diaryLabel = new DiaryLabel();
        try{
            diaryLabel.status = 9;
            diaryLabel.anchor = rs.getLong("anchor");
            diaryLabel.id = rs.getInt("id");
            diaryLabel.diary = new Diary();
            diaryLabel.diary.id = rs.getInt("diary");
            diaryLabel.label = new Label();
            diaryLabel.label.id = rs.getInt("label");
        }catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return diaryLabel;
        }
    }
    public static Label toLabel(ResultSet rs){
        //status默认设置为9
        Label label = new Label();
        try{
            label.status = 9;
            label.anchor = rs.getLong("anchor");
            label.id = rs.getInt("id");
            label.labelname = rs.getString("labelname");
        }catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return label;
        }
    }

    public static Sentence toSentence(ResultSet rs) {
        //status默认设置成为9
        Sentence sentence = new Sentence();
        try {
            sentence.anchor = rs.getLong("anchor");
            sentence.status = 9;
            sentence.id = rs.getInt("id");
            sentence.sentencebook.id = rs.getInt("sentencebook");
            sentence.htmlText = rs.getString("htmlText");
            sentence.text = rs.getString("text");
            sentence.date = rs.getLong("date");
            sentence.islike = rs.getBoolean("islike");
            sentence.lineSpacingMultiplier = rs.getInt("lineSpacingMultiplier");
            sentence.lineSpacingExtra = rs.getInt("lineSpacingExtra");
            sentence.textSize = rs.getFloat("textSize");
            sentence.textAlignment = rs.getInt("textAlignment");
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return sentence;
        }
    }
    public static Sentencebook toSentencebook(ResultSet rs){
        //status默认设置为9
        Sentencebook sentencebook = new Sentencebook();
        try{
            sentencebook.status = 9;
            sentencebook.anchor = rs.getLong("anchor");
            sentencebook.id = rs.getInt("id");
            sentencebook.sentencebookName = rs.getString("sentencebookName");
            sentencebook.description = rs.getString("description");
        }catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return sentencebook;
        }
    }
    public static SentenceLabel toSentenceLabel(ResultSet rs){
        //status默认设置为9
        SentenceLabel sentenceLabel = new SentenceLabel();
        try{
            sentenceLabel.status = 9;
            sentenceLabel.anchor = rs.getLong("anchor");
            sentenceLabel.id = rs.getInt("id");
            sentenceLabel.sentence = new Sentence();
            sentenceLabel.sentence.id = rs.getInt("sentence");
            sentenceLabel.label = new Label();
            sentenceLabel.label.id = rs.getInt("label");
        }catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            return sentenceLabel;
        }
    }

}
