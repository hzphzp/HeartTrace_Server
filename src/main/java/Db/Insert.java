package Db;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {
    public static <T> boolean insert(DatabaseAdapter adapter, T cls, String username, Long anchor, boolean delete){
        boolean flag = false;
        try {
            PreparedStatement pstm = adapter.connection.prepareStatement(createSql(cls.getClass(), delete));
            int index = 1;
            for(Field field : cls.getClass().getDeclaredFields()){
                if(index == 1){
                    //将Status项变成username项存入服务器端数据库
                    pstm.setString(1, username);
                    index++;
                    continue;
                }
                field.setAccessible(true);
                Object obj = field.get(cls);
                Update.setObject(pstm, index, obj);
                index++;
            }
            pstm.setLong(index, anchor);//增加anchor这一项
            int result = pstm.executeUpdate();
            flag = result > 0;
        }catch (SQLException se){
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
        String str = "";
        for(Field s : cls.getDeclaredFields()){
            str += "?, ";
        }
        str +="?, ";//增加anchor的位置
        str = str.substring(0, str.length()-2);
        String sql;
        if(!delete) {
            sql = "INSERT INTO " + cls.getSimpleName() + " VALUE(" + str + ");";
        }
        else {
            sql = "INSERT INTO " + cls.getSimpleName()+"_delete" + " VALUE(" + str + ");";
        }
        return sql;
    }

}
