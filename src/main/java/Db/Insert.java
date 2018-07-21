package Db;

import Json.dbJson.Diary;

import java.sql.SQLException;
import java.sql.Statement;

public class Insert {
    public static void InsertDiary(Statement statement, Diary diary){
        String sql = String.format("INSERT INTO Diary VALUES(%d, %d, %d, %s, %s, %d, %d, %f, %d, %d, %f, %d)",
                diary.anchor, diary.id, diary.diarybook.id, diary.htmlText, diary.text, diary.date,
                diary.islike, diary.letterSpacing, diary.lineSpacingMultiplier, diary.lineSpacingExtra,
                diary.textSize, diary.textAlignment);
        try {
            statement.executeUpdate(sql);

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }
}
