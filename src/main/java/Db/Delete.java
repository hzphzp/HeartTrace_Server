package Db;

import Json.dbJson.Diary;
import org.apache.commons.codec.language.DaitchMokotoffSoundex;

import java.sql.SQLException;
import java.sql.Statement;

public class Delete {
    public static void deleteDiary(Statement statement, String username, Diary diary){
        String sql = String.format("DELETE FROM Diary WHERE username = \"$s\" AND  id = %d", username, diary.id);
        try {
            statement.executeUpdate(sql);
            sql = String.format("INSERT INTO Diary_delete VALUES(%d, %d, %d, %s, %s, %d, %d, %f, %d, %d, %f, %d)",
                diary.anchor, diary.id, diary.diarybook.id, diary.htmlText, diary.text, diary.date,
                diary.islike, diary.letterSpacing, diary.lineSpacingMultiplier, diary.lineSpacingExtra,
                diary.textSize, diary.textAlignment);
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
