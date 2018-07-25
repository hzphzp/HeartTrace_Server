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
                    Field idField = clz.getField("id)");
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
                            if(anchorField.get(pattern) == anchorField.get(patternInServer)){
                                //两个数据之前已经同步好了, 直接利用status进行更新不会发生冲突
                                if(statusField.getInt(pattern) ==  -1){
                                    anchorField.set(pattern, (new Date()).getTime());
                                    anchorField.set(patternInServer, (new Date()).getTime());
                                    Delete.delete(adapter, clz, username, idField.getInt(pattern), false);
                                    Insert.insert(adapter, patternInServer, username, true);
                                    statusField.set(pattern, 9);
                                    addMethod.invoke(field.get(syncback), pattern);
                                }
                                if(statusField.getInt(pattern) == 1) {
                                    anchorField.set(pattern, (new Date()).getTime());
                                    anchorField.set(patternInServer, (new Date()).getTime());
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
                                        anchorField.set(patternInServer, (new Date()).getTime());
                                        anchorField.set(pattern, (new Date()).getTime());
                                        Update.update(adapter, pattern, username, true);
                                        statusField.set(pattern, -1);
                                        addMethod.invoke(field.get(syncback), pattern);//status -1 发回本地，然本地删除
                                    }
                                    else {
                                        //仍然在服务器端的数据库中，可能出现的冲突时文本的修改，这里以服务器为主
                                        anchorField.set(patternInServer, (new Date()).getTime());
                                        anchorField.set(pattern, (new Date()).getTime());
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
        Long maxanchor = Long.parseLong(request.getParameter("maxanchor"));
        String username = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        JavaWebToken.verifyToken(token, username, modelnum);
        Json.Sync syncback = new Json.Sync();
        DatabaseAdapter adapter = new DatabaseAdapter();
        try{
            String sql = String.format("SELECT * FROM Diary WHERE username = %s AND anchor > %d", username, maxanchor);
            ResultSet resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.DiaryList.add(SqltoJson.toDiary(resultSet));
            }
            sql = String.format("SELECT * FROM Diarybook WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.DiarybookList.add(SqltoJson.toDiarybook(resultSet));
            }
            sql = String.format("SELECT * FROM DiaryLabel WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.DiaryLabelList.add(SqltoJson.toDiaryLabel(resultSet));
            }
            sql = String.format("SELECT * FROM Label WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.LabelList.add(SqltoJson.toLabel(resultSet));
            }
            sql = String.format("SELECT * FROM Sentence WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.SentenceList.add(SqltoJson.toSentence(resultSet));
            }
            sql = String.format("SELECT * FROM Sentencebook WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.SentencebookList.add(SqltoJson.toSentencebook(resultSet));
            }
            sql = String.format("SELECT * FROM SentenceLabel WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                syncback.SentenceLabelList.add(SqltoJson.toSentenceLabel(resultSet));
            }


            sql = String.format("SELECT * FROM Diary_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            Diary diary;
            Diarybook diarybook;
            DiaryLabel diaryLabel;
            Label label;
            Sentence sentence;
            Sentencebook sentencebook;
            SentenceLabel sentenceLabel;
            while(resultSet.next()){
                diary = SqltoJson.toDiary(resultSet);
                diary.status = -1;
                syncback.DiaryList.add(diary);
            }
            sql = String.format("SELECT * FROM Diarybook_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                diarybook = SqltoJson.toDiarybook(resultSet);
                diarybook.status = -1;
                syncback.DiarybookList.add(diarybook);
            }
            sql = String.format("SELECT * FROM DiaryLabel_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                diaryLabel = SqltoJson.toDiaryLabel(resultSet);
                diaryLabel.status = -1;
                syncback.DiaryLabelList.add(diaryLabel);
            }
            sql = String.format("SELECT * FROM Label_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                label = SqltoJson.toLabel(resultSet);
                label.status = -1;
                syncback.LabelList.add(label);
            }
            sql = String.format("SELECT * FROM Sentence_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                sentence = SqltoJson.toSentence(resultSet);
                sentence.status = -1;
                syncback.SentenceList.add(sentence);
            }
            sql = String.format("SELECT * FROM Sentencebook_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                sentencebook = SqltoJson.toSentencebook(resultSet);
                sentencebook.status = -1;
                syncback.SentencebookList.add(sentencebook);
            }
            sql = String.format("SELECT * FROM SentenceLabel_delete WHERE username = %s AND anchor > %d",username,maxanchor);
            resultSet = adapter.statement.executeQuery(sql);
            while(resultSet.next()){
                sentenceLabel = SqltoJson.toSentenceLabel(resultSet);
                syncback.SentenceLabelList.add(sentenceLabel);
            }


            Gson gson = new Gson();
            Output.output(gson.toJson(syncback), response);
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
