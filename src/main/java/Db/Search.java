package Db;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {
    public static <T> List<T> searchWithAnchor(DatabaseAdapter adapter, Class<T> cls, Long anchor){
        List<T> list = new ArrayList<T>();
        try{
            PreparedStatement pstm = adapter.connection.prepareStatement(createSqlWithAnchor(cls, false));
            pstm.setLong(1, anchor);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                T t = cls.newInstance();
                int index = 1;
                for(Field field : cls.getDeclaredFields()){
                    if(index == 1){
                        field.set(t, 9);
                        index++;
                        continue;
                    }
                    field.setAccessible(true);
                    fieldSet(rs, t, field);
                    index++;
                }
                list.add(t);
            }
            pstm = adapter.connection.prepareStatement(createSqlWithAnchor(cls, true));
            pstm.setLong(1, anchor);
            rs = pstm.executeQuery();
            while (rs.next()){
                T t = cls.newInstance();
                int index = 1;
                for(Field field : cls.getDeclaredFields()){
                    if(index == 1){
                        field.set(t, -1);
                        index++;
                        continue;
                    }
                    field.setAccessible(true);
                    fieldSet(rs, t, field);
                    index++;
                }
                list.add(t);
            }
        } catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        finally {
            return list;
        }

    }

    private static <T> void fieldSet(ResultSet rs, T t, Field field) throws IllegalAccessException, NoSuchFieldException, SQLException {
        try {
            field.set(t, rs.getObject(field.getName()));
        }catch (Exception mye){
            Object obj = field.get(t);
            Field field1 = obj.getClass().getDeclaredField("id");
            field1.set(obj, rs.getObject(field.getName()));
            field.set(t, obj);
        }
    }
public static <T> Long search_anchor(DatabaseAdapter adapter, Class<T> cls, String username, long id, boolean delete){
        //T t = null;
        Long anchor = null;
        try {
            PreparedStatement pstm = adapter.connection.prepareStatement(createSql(cls, delete));
            pstm.setString(1, username);
            pstm.setLong(2, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                anchor = rs.getLong("anchor");
            }
        } catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        finally {
            return anchor;
        }

    }
    public static <T> T search(DatabaseAdapter adapter, Class<T> cls, String username, long id, boolean delete){
        T t = null;
        try {
            PreparedStatement pstm = adapter.connection.prepareStatement(createSql(cls, delete));
            pstm.setString(1, username);
            pstm.setLong(2, id);
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
                    fieldSet(rs,  t, field);
                    index++;
                }
            }
        } catch(Exception e){
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
    private static String createSqlWithAnchor(Class<?> cls, boolean delete){
        String sql;
        if(!delete) {
            sql = "SELECT * FROM " + cls.getSimpleName() + " WHERE anchor > ?";
        }
        else {
            sql = "SELECT * FROM " + cls.getSimpleName()+"_delete" + " WHERE anchor > ?";
        }
        return sql;
    }
}
