package Db;

import jdk.nashorn.internal.scripts.JD;

import java.sql.*;
public class DatabaseAdapter {
    private static final String JDBC_DRIVER= "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://l22.152.195.134:3306/HeartTrace";
    private static final String USER = "root";
    private static final String PASSWORD = "hearttrace3";

    public Connection connection = null;
    public Statement statement = null;
    public DatabaseAdapter(){
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection("jdbc:mysql://122.152.195.134:3306/HeartTrace?"
                    + "user=root&password=hearttrace3");
            statement = connection.createStatement();

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }
    public void Destroy(){
        try {
            connection.close();
            statement.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }
}

