package Servlet;

import java.sql.*;
public class DatabaseAdapter {
    private static final String JDBC_DRIVER= "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql:/122.152.195.134/TEST";
    private static final String USER = "root";
    private static final String PASSWORD = "hearttrace3";

    public Connection connection = null;
    public Statement statement = null;
    public DatabaseAdapter(){
        try{
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            statement = connection.createStatement();

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(statement!=null) statement.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(connection!=null) connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}

