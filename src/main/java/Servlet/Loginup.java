package Servlet;

import Db.DatabaseAdapter;
import Json.LoginBack;
import Jwt.JavaWebToken;
import Output.Output;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Servlet.Loginup")
public class Loginup extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String modelnum = request.getParameter("modelnum");
        DatabaseAdapter adapter = new DatabaseAdapter();
        String mysql = "SELECT * FROM UserList WHERE username =  '" + username +"';";
        try {
            ResultSet rs = adapter.statement.executeQuery(mysql);
            if(!rs.next()) {
                //用户名不存在，请检查后输入
                Gson gson = new Gson();
                LoginBack loginBack = new LoginBack();
                loginBack.success = false;
                loginBack.msg = "登录失败，用户不存在，请检查后重试";
                loginBack.token = null;
                Output.output(gson.toJson(loginBack), response);
                log(username+"注册失败， 已经存在");
            }
            else{
                //用户名存在， 检查密码是否正确
                String password1 = rs.getString("password");
                if(!password1.equals(password)){
                    //密码不正确，请检查后输入
                    Gson gson = new Gson();
                    LoginBack loginBack = new LoginBack();
                    loginBack.success = false;
                    loginBack.msg = "登录失败，密码错误，请检查后重试";
                    loginBack.token = null;
                    Output.output(gson.toJson(loginBack), response);
                }
                else {
                    //登录成功，放回token
                    Gson gson = new Gson();
                    LoginBack loginBack = new LoginBack();
                    loginBack.success = true;
                    loginBack.msg = "登录成功，放回token";
                    loginBack.token = JavaWebToken.createToken(username, modelnum);
                    Output.output(gson.toJson(loginBack), response);
                }
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
