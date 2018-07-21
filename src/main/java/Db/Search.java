package Db;

import Json.SqltoJson;
import Json.dbJson.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Search {
    public static Diary SearchDiary(Statement statement, String username, int id){
        String sql = "SELECT * FORM Diary WHERE username = \"/s\" AND id = %d".format(username, id);
        Diary diary = null;
        try{
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                diary = SqltoJson.toDiary(rs);
                diary.status = 9;
            }
            else{
                sql = "SELECT * FORM Diary_delete WHERE username = \"/s\" AND id = %d".format(username, id);
                rs = statement.executeQuery(sql);
                if(rs.next()){
                    diary = SqltoJson.toDiary(rs);
                    diary.status = -1;
                }
                else{
                    //在数据库中查找不到相关的数据
                    diary = null;
                }
            }
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return diary;
    }
}
