package Servlet;

import Db.*;
import Jwt.JavaWebToken;
import Output.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.Date;

@WebServlet(name = "Servlet.Sync1")
public class Sync1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        long anchor;
        long now = (new Date()).getTime();
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        String content = request.getParameter("content");
        long newestUpdate = Long.parseLong(request.getParameter("anchor"));
        JavaWebToken.verifyToken(token, username, modelnum);
        DatabaseAdapter adapter = new DatabaseAdapter();
        Json.Sync syncback = new Json.Sync();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        //java.lang.reflect.Type classType = new TypeToken<Json.Sync>() {}.getType();
        //Json.Sync sync = gson.fromJson(content, classType);
        Json.Sync sync = gson.fromJson(content, Json.Sync.class);
        try {
            for (Field field : sync.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get(sync);
                Type t = field.getGenericType();
                if (t instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) t;
                    Class clz = (Class) pt.getActualTypeArguments()[0];//List里面的示例的类型
                    Class clazz = obj.getClass();//List这个类型
                    Field modifiedField = clz.getField("modified");
                    Field statusField = clz.getField("status");
                    Field idField = clz.getField("id");
                    Method sizeMethod = clazz.getDeclaredMethod("size");
                    sizeMethod.setAccessible(true);
                    Method getMethod = clazz.getDeclaredMethod("get", int.class);
                    getMethod.setAccessible(true);
                    Method addMethod = clazz.getDeclaredMethod("add", Object.class);
                    addMethod.setAccessible(true);
                    int size = (Integer) sizeMethod.invoke(obj);
                    for (int i = 0; i < size; i++) {
                        Object pattern = getMethod.invoke(obj, i);
                        if (0 == statusField.getInt(pattern)) {
                            //新增的
                            //直接插入， 其中anchor更新成请求接收到的时间
                            Insert.insert(adapter, pattern, username, now, false);
                        }
                        else{
                            //不是新增的，这里可能存在冲突
                            Object patternInServer = Search.search(adapter, clz, username, idField.getLong(pattern), false);
                            anchor = Search.search_anchor(adapter, clz, username, idField.getLong(pattern), false);
                            if(patternInServer == null){
                                //没找到，在垃圾箱中找找
                                patternInServer = Search.search(adapter, clz, username, idField.getLong(pattern), true);
                                anchor = Search.search_anchor(adapter, clz, username, idField.getLong(pattern), true);
                            }
                            if(patternInServer == null){
                                //在服务器数据库和垃圾箱中都没找到,一定是客户端的代码写错了
                                modifiedField.set(pattern, (new Date()).getTime());
                                statusField.set(pattern, -1);
                                continue;
                            }
                            if(modifiedField.getLong(pattern) > modifiedField.getLong(patternInServer)){
                                //本地的这条记录更新，保存本地的
                                if(statusField.getInt(patternInServer) == -1) {
                                    //这条记录是在垃圾桶里面找到的，先还原到原来的数据库
                                    Delete.delete(adapter, clz, username, idField.getLong(patternInServer), true);
                                    Insert.insert(adapter, patternInServer, username, anchor, false);
                                }

                                if(statusField.getInt(pattern) == -1){
                                    //客户端删除这个记录
                                    Delete.delete(adapter, clz, username, idField.getLong(pattern), false);
                                    Insert.insert(adapter, pattern, username, now, true);
                                }
                                else{
                                    //客户更新这条记录
                                    Update.update(adapter, pattern, username, now, false);
                                }
                            }
                            else{
                                //服务器端的记录更新，保存服务器端的
                                if(statusField.getInt(patternInServer) == -1) {
                                    //这条记录是在垃圾桶里面找到的，先还原到原来的数据库
                                    Update.update(adapter, patternInServer, username, now, true);
                                }
                                else {
                                    //这条记录不是在垃圾桶中找到的
                                    Update.update(adapter, patternInServer, username, now, false);
                                }
                            }
                        }
                    }
                    //处理完了一个其中一个list的push更新
                    //现在编写返回的syncback
                    //就是这一个list中的anchor大于传来的newestUpdate里面
                    field.set(syncback, Search.searchWithAnchor(adapter, clz, username, newestUpdate));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String json = gson.toJson(syncback);
        response.addHeader("anchor", Long.toString(now));

        Output.output(json, response);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
