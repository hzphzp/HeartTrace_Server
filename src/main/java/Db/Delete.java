package Db;

import Json.dbJson.Diary;
import org.apache.commons.codec.language.DaitchMokotoffSoundex;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {
    public static boolean delete(DatabaseAdapter adapter, Class<?> cls, String username, long id, boolean delete) {
        boolean flag = false;
        try{
            PreparedStatement pstm = adapter.connection.prepareStatement(createSql(cls, delete));
            pstm.setString(1, username);
            pstm.setLong(2, id);
            int result = pstm.executeUpdate();
            flag = result > 0;
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally {
            return flag;
        }
    }
    private static String createSql(Class<?> cls, boolean delete){
        String sql;
        if(!delete) {
            sql = "DELETE FROM " + cls.getSimpleName() + " WHERE username = ? AND id = ?";
        }
        else {
            sql = "DELETE FROM " + cls.getSimpleName()+"_delete" + " WHERE username = ? AND id = ?";
        }
        return sql;
    }
}
