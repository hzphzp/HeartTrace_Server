package Db;

import Json.SqltoJson;
import Json.dbJson.*;

import java.io.NotSerializableException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
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
    public static <T> T search(DatabaseAdapter adapter, Class<T> cls, String username, int id, boolean delete){
        T t = null;
        try {
            PreparedStatement pstm = adapter.connection.prepareStatement(createSql(cls, delete));
            pstm.setString(1, username);
            pstm.setInt(2, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                t = cls.newInstance();
                int index = 1;
                for (Field field : cls.getDeclaredFields()) {
                    if(index == 1){
                        field.set(t, 9);
                        if(delete){
                            field.set(t, -1);
                        }
                        index++;
                        continue;
                    }
                    field.setAccessible(true);
                    try {
                        field.set(t, rs.getObject(field.getName()));
                    }catch (Exception mye){
                        Object obj = field.get(t);
                        Field field1 = obj.getClass().getDeclaredField("id");
                        field1.set(obj, rs.getObject(field.getName()));
                        field.set(t, obj);
                    }
                    index++;
                }
            }
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally {
            return t;
        }

    }
    private static String createSql(Class<?> cls, boolean delete){
        String sql;
        if(!delete) {
            sql = "SELECT * FROM " + cls.getSimpleName() + " WHERE username = ? AND id = ?";
        }
        else {
            sql = "SELECT * FROM " + cls.getSimpleName()+ "_delete" + " WHERE username = ? AND id = ?";
        }
        return sql;
    }
}
