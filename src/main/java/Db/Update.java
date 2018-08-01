package Db;

import Json.dbJson.Diary;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {

    public static<T> boolean update(DatabaseAdapter adapter, T cls, String username, long anchor, boolean delete){
        boolean flag = false;
        try{
            PreparedStatement pstm = adapter.connection.prepareStatement(createSql(cls.getClass(), delete));
            int index = 1;
            for(Field field : cls.getClass().getDeclaredFields()){
                if(index == 1){
                    pstm.setString(index, username);
                    index++;
                    continue;
                }
                field.setAccessible(true);
                Object obj = field.get(cls);
                setObject(pstm, index, obj);
                index++;
            }
            pstm.setLong(index, anchor);//增加anchor这一想的的填坑
            pstm.setString(index+1, username);
            pstm.setObject(index+2, cls.getClass().getField("id").get(cls));
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

    static void setObject(PreparedStatement pstm, int index, Object obj) throws NoSuchFieldException, IllegalAccessException, SQLException {
        Field field;
        try {
            pstm.setObject(index, obj);
        }catch(Exception nse){
            field = obj.getClass().getField("id");
            long id = field.getLong(obj);
            pstm.setObject(index, id);
        }
    }

    private static String createSql(Class<?> cls, boolean delete){
        String str = "";
        String sql;
        int index = 1;
        for(Field field : cls.getDeclaredFields()){
            if(index == 1){
                str += "username=?, ";
                index++;
                continue;
            }
            str += field.getName() + "=?, ";
            index++;
        }
        str += "anchor=?, ";
        str = str.substring(0, str.length()-2);
        if(!delete) {
            sql = "UPDATE " + cls.getSimpleName() + " SET " + str + " WHERE username = ? AND id = ?";
        }
        else {
            sql = "UPDATE " + cls.getSimpleName() + "_delete" + " SET " + str + " WHERE username = ? AND id = ?";
        }
        return sql;
    }
}
