package Servlet;

import ClassForTest.GenJson;
import ClassForTest.HttpForTest;
import Db.*;
import com.google.gson.Gson;
import javafx.scene.chart.PieChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.security.UnresolvedPermission;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SyncTest {

    @Before
    public void setUp() throws Exception {
        String Url = "http://localhost:8080/Servlet.Sync";
        Map<String, String > map = new HashMap<String, String>();
        map.put("username", "huangzp");
        map.put("modelnum", "honor7");
        map.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9.8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw");
        map.put("content", GenJson.fromDefault());
        //HttpForTest.sendPost(Url, map);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() {
        Json.Sync syncback = new Json.Sync();
        String username = "huangzp";
        DatabaseAdapter adapter = new DatabaseAdapter();
        String json = GenJson.fromDefault();
        Gson gson = new Gson();
        Json.Sync sync = gson.fromJson(json, Json.Sync.class);
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
    }

    @Test
    public void doGet() {
    }
}