package Servlet;
import Db.*;
import Json.*;
import Json.dbJson.*;
import Jwt.JavaWebToken;
import Output.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "Servlet.Sync")
public class Sync extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        String content = request.getParameter("content");
        JavaWebToken.verifyToken(token, username, modelnum);
        DatabaseAdapter adapter = new DatabaseAdapter();
        Json.Sync syncback = new Json.Sync();
        Gson gson = new Gson();
        //java.lang.reflect.Type classType = new TypeToken<Json.Sync>() {}.getType();
        //Json.Sync sync = gson.fromJson(content, classType);
        Json.Sync sync = gson.fromJson(content, Json.Sync.class);
        try{
            for(Field field : sync.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object obj = field.get(sync);
                Type t = field.getGenericType();
                if(t instanceof ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) t;
                    Class clz = (Class) pt.getActualTypeArguments()[0];//List里面的示例的类型
                    Class clazz = obj.getClass();//List这个类型
                    Field anchorField = clz.getField("anchor");
                    Field statusField = clz.getField("status");
                    Field idField = clz.getField("id");
                    Method sizeMethod = clazz.getDeclaredMethod("size");
                    Method getMethod = clazz.getDeclaredMethod("get", int.class);
                    getMethod.setAccessible(true);
                    Method addMethod = clazz.getDeclaredMethod("add", Object.class);
                    addMethod.setAccessible(true);
                    int size = (Integer) sizeMethod.invoke(obj);
                    for(int i = 0; i < size; i++){
                        Object pattern = getMethod.invoke(obj, i);
                        if(0 == statusField.getInt(pattern)){
                            //新增的
                            anchorField.set(pattern, (new Date()).getTime());
                            Insert.insert(adapter, pattern, username, false);
                            statusField.set(pattern, 9);
                            addMethod.invoke(field.get(syncback), pattern);
                        }
                        else {
                            //不是新增的
                            Object patternInServer = Search.search(adapter, clz, username, idField.getInt(pattern), false);
                            if(patternInServer == null){
                                //没找到，在垃圾箱中找找
                                patternInServer = Search.search(adapter, clz, username, idField.getInt(pattern), true);
                            }
                            if(patternInServer == null){
                                //在服务器数据库和垃圾箱中都没找到,一定是客户端的代码写错了
                                anchorField.set(pattern, (new Date()).getTime());
                                statusField.set(pattern, -1);
                                addMethod.invoke(field.get(syncback), pattern);
                                continue;
                            }
                            if(anchorField.get(pattern).equals(anchorField.get(patternInServer))) {
                                //两个数据之前已经同步好了, 直接利用status进行更新不会发生冲突
                                if(statusField.getInt(pattern) ==  -1){
                                    anchorField.set(pattern, (new Date()).getTime());
                                    anchorField.set(patternInServer, anchorField.get(pattern));
                                    Delete.delete(adapter, clz, username, idField.getInt(pattern), false);
                                    Insert.insert(adapter, patternInServer, username, true);
                                    statusField.set(pattern, -1);
                                    addMethod.invoke(field.get(syncback), pattern);
                                }
                                else if(statusField.getInt(pattern) == 1) {
                                    anchorField.set(pattern, (new Date()).getTime());
                                    anchorField.set(patternInServer, anchorField.get(pattern));
                                    Update.update(adapter, pattern, username, false);
                                    statusField.set(pattern, 9);
                                    addMethod.invoke(field.get(syncback), pattern);
                                }
                                else{
                                    //不可能有这种情况，一定是客户端代码写错了
                                    anchorField.set(pattern, (new Date()).getTime());
                                    statusField.set(pattern, -1);
                                    addMethod.invoke(field.get(syncback), pattern);
                                }
                            }
                            else {
                                //表示之前本地就没有更新到最新的版本，下面的操作可能存在冲突，目前考虑冲突全部以服务器端优先
                                    if(statusField.getInt(patternInServer) == -1){
                                        //是在垃圾桶中找到这个记录的，证明之前在其他的客户端中对这一项进行了删除,这里对服务器进行更新，同时删除本地
                                        anchorField.set(pattern, (new Date()).getTime());
                                        anchorField.set(patternInServer, anchorField.get(pattern));
                                        Update.update(adapter, pattern, username, true);
                                        statusField.set(pattern, -1);
                                        addMethod.invoke(field.get(syncback), pattern);//status -1 发回本地，然本地删除
                                    }
                                    else {
                                        //仍然在服务器端的数据库中，可能出现的冲突时文本的修改，这里以服务器为主
                                        anchorField.set(pattern, (new Date()).getTime());
                                        anchorField.set(patternInServer, anchorField.get(pattern));
                                        statusField.set(patternInServer, 9);
                                        addMethod.invoke(field.get(syncback), patternInServer);
                                    }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Output.output(gson.toJson(syncback), response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        Long maxanchor = Long.parseLong(request.getParameter("maxanchor"));
        JavaWebToken.verifyToken(token, username, modelnum);
        Json.Sync syncback = new Json.Sync();
        DatabaseAdapter adapter = new DatabaseAdapter();
        syncback.DiaryList = Search.searchForGet(adapter, Diary.class, maxanchor);
        syncback.DiarybookList = Search.searchForGet(adapter, Diarybook.class, maxanchor);
        syncback.DiaryLabelList = Search.searchForGet(adapter, DiaryLabel.class, maxanchor);
        syncback.LabelList = Search.searchForGet(adapter, Label.class, maxanchor);
        syncback.SentenceList = Search.searchForGet(adapter, Sentence.class, maxanchor);
        syncback.SentencebookList = Search.searchForGet(adapter, Sentencebook.class, maxanchor);
        syncback.SentenceLabelList = Search.searchForGet(adapter, SentenceLabel.class, maxanchor);
        Gson gson = new Gson();
        Output.output(gson.toJson(syncback), response);
        adapter.Destroy();
    }

}
