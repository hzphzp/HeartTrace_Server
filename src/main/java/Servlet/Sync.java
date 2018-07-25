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
        if(sync.DiaryList != null) {
            for (Diary diary : sync.DiaryList) {
                if (diary.status == 0) {
                    Insert.insert(adapter, diary, username, false);
                    diary.anchor = (new Date()).getTime();
                    syncback.DiaryList.add(diary);
                } else {
                    //该数据不是新增的
                    Diary diary1 = Search.SearchDiary(adapter.statement, username, diary.id);
                    if (diary1 != null) {
                        //在数据库或者是垃圾箱中找到了这个id
                        if (diary1.anchor == diary.anchor) {
                            //这个数据之前同步过了，直接通过status的值进行更新，不会发生冲突
                            if (diary.status == -1) {
                                diary.anchor = (new Date()).getTime();
                                diary1.anchor = diary.anchor;
                                Delete.delete(adapter, Diary.class, username, diary.id, false);
                                syncback.DiaryList.add(diary);
                            }
                            if (diary.status == 1) {
                                diary.anchor = (new Date()).getTime();
                                diary1.anchor = diary.anchor;
                                Update.update(adapter, diary, "username", false);
                                syncback.DiaryList.add(diary);
                            }
                        } else {
                            //这个数据在之前就没有及时同步，操作时可能发生冲突
                            if (diary1.status == -1) {
                                //其他client已经通过同步将这一项删除
                                diary1.anchor = (new Date()).getTime();
                                //Update.updateDiary_delete(adapter.statement, username, diary1);
                                //TODO
                                syncback.DiaryList.add(diary1);//status为-1，发回本地，让本地删除， 本地删除
                            } else {
                                diary1.anchor = (new Date()).getTime();
                                //Update.updateDiary(adapter.statement, username, diary1);
                                //TODO
                                syncback.DiaryList.add(diary1);
                            }
                        }
                    } else {
                        //在数据库中无法查出这条数据, 出现问题，本地删除
                        diary.anchor = (new Date()).getTime();
                        diary.status = -1;
                        syncback.DiaryList.add(diary);
                    }
                }
            }
        }
        Output.output(gson.toJson(syncback), response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Long maxanchor = Long.parseLong(request.getParameter("maxanchor"));
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
        }*/
    }

}
