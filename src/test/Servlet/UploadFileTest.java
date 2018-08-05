package Servlet;

import ClassForTest.Base64ImageUtil;
import ClassForTest.HttpForTest;
import ClassForTest.Uploader;
import Json.MyFile;
import Json.MyFileList;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.*;

public class UploadFileTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() throws IOException {
        Map<String,String>  map = new HashMap<>();
        map.put("username", "huangzp");
        map.put("modelnum", "honor7");
        map.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiJodWFuZ3pwIiwic3ViIjoiaG9ub3I3IiwiaXNzIjoiaHVhbmd6cCJ9" +
                ".8iY9N5TvSdKUtU6IUxsJCg2OX6unfM3kiJNHVAL5duw");
        map.put("anchor", "-1");
        File dir = new File("D:\\Pictures\\desktop");
        File[] files = dir.listFiles();
        MyFileList inFiles = new MyFileList();
        for(int i = 0; i < files.length; i++){

            MyFile myFile = new MyFile();
            myFile.filename = files[i].getName();
            /*ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, myFile.type, out);*/
            myFile.content = Base64ImageUtil.encodeToString(files[i], "jpg");
            inFiles.files.add(myFile);

        }
        Gson gson = new Gson();
        String content = gson.toJson(inFiles, MyFileList.class);
        map.put("content", content);
        //String result = HttpForTest.sendPost("http://122.152.195.134:8080/HeartTrace_Server_war/Servlet.UploadFile", map);
        String result = HttpForTest.sendPost("http://localhost:8080/Servlet.UploadFile", map);
        boolean f = result == content;
        MyFileList outFiles = gson.fromJson(result, MyFileList.class);
        f = inFiles.files.equals(outFiles.files);
        f = inFiles.files.get(0).content.equals(outFiles.files.get(0).content);
        if(outFiles != null){
            for(MyFile myFile : outFiles.files){
                BufferedImage image = Base64ImageUtil.decodeToImage(myFile.content);
                if(image == null){
                    continue;
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                File dir1 = new File("D:\\test");
                if(!dir1.exists()){
                    dir1.mkdir();
                }
                File file = new File(dir1 + File.separator + myFile.filename);
                ImageIO.write(image, "jpg", file);
            }
        }

    }


}