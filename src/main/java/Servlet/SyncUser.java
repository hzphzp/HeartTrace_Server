package Servlet;

import Db.DatabaseAdapter;
import Json.dbJson.UserList;
import Jwt.JavaWebToken;
import Output.Output;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Servlet.SyncUser")
public class SyncUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        String content = request.getParameter("content");
        JavaWebToken.verifyToken(token, username, modelnum);
        DatabaseAdapter adapter = new DatabaseAdapter();
        Gson gson = new Gson();
        Json.dbJson.UserList user = gson.fromJson(content, Json.dbJson.UserList.class);
        Json.dbJson.UserList userback = new Json.dbJson.UserList();
        try {
            Json.dbJson.UserList userInServer = search(adapter, username);
            if(userInServer.modified > user.modified){
                //服务器端比较新，保留服务器端的数据
                userback = userInServer;
            }
            else{
                //客户端比较性，服务器update
                update(adapter, user);
                userback = user;
            }
            userback.password = null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String json = gson.toJson(userback);
        Output.output(json, response);

    }



    public static Json.dbJson.UserList search(DatabaseAdapter adapter, String  username) throws SQLException, IllegalAccessException {
        Json.dbJson.UserList user = new UserList();
        String sql = "SELECT * FROM UserList WHERE username=?";
        PreparedStatement pstm = adapter.connection.prepareStatement(sql);
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            //找到这个
            for(Field field : Json.dbJson.UserList.class.getDeclaredFields()){
                field.set(user, rs.getObject(field.getName()));
            }
        }
        return user;
    }
    public static boolean insert(DatabaseAdapter adapter, Json.dbJson.UserList user) throws SQLException, IllegalAccessException {
        String str = "";
        for(Field field : user.getClass().getDeclaredFields()){
            str += "?, ";
        }
        str = str.substring(0, str.length()-2);
        String sql = "INSERT INTO UserList VALUE(" + str + ");";
        PreparedStatement pstm = adapter.connection.prepareStatement(sql);
        int index = 1;
        for(Field field : user.getClass().getDeclaredFields()){
            pstm.setObject(index, field.get(user));
            index++;
        }
        int result = pstm.executeUpdate();
        return (result > 0);
    }
    public static boolean update(DatabaseAdapter adapter, Json.dbJson.UserList user) throws SQLException, IllegalAccessException, NoSuchFieldException {
        String str = "";
        for (Field field : user.getClass().getDeclaredFields()){
            if(field.getName() == "password"){
                continue;
            }
            str += field.getName() + "=?, ";
        }
        str = str.substring(0, str.length()-2);
        String sql = "UPDATE UserList SET " + str + " WHERE username=?";
        PreparedStatement pstm = adapter.connection.prepareStatement(sql);
        int index = 1;
        for(Field field : user.getClass().getDeclaredFields()){
            if(field.getName() == "password"){
                continue;
            }
            pstm.setObject(index, field.get(user));
            index++;
        }
        pstm.setObject(index, user.getClass().getField("username").get(user));
        int result = pstm.executeUpdate();
        return (result>0);
    }
}
