package Servlet;

import Db.DatabaseAdapter;
import Json.LoginBack;
import Output.Output;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Output.Output.output;

@WebServlet(name = "Servlet.Signin")
public class Signin extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DatabaseAdapter adapter = new DatabaseAdapter();
        String mysql = "SELECT username FROM User WHERE username =  '" + username +"';";
        try {
            ResultSet rs = adapter.statement.executeQuery(mysql);
            if(!rs.next()) {
                //用户名不存在，可以成功注册
                mysql = "INSERT INTO User(username, password) VALUES ('" + username + "', '" + password + "');";
                adapter.statement.executeUpdate(mysql);
                Gson gson = new Gson();
                LoginBack loginBack = new LoginBack();
                loginBack.success = true;
                loginBack.msg = "注册成功";
                loginBack.token = null;
                Output.output(gson.toJson(loginBack), response);
            }
            else{
                //用户名存在， 不能注册，请更换用户名
                Gson gson = new Gson();
                LoginBack loginBack = new LoginBack();
                loginBack.success = false;
                loginBack.msg = "注册失败，该用户名已经被使用，请更换";
                loginBack.token = null;
                Output.output(gson.toJson(loginBack), response);
            }
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally {
            adapter.Destroy();
        }
    }
}

