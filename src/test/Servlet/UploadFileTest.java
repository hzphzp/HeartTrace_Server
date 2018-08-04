package Servlet;

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
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static Servlet.UploadFile.setType;
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
        map.put("anchor", String.valueOf((new Date()).getTime()));
        File dir = new File("D:\\Pictures\\desktop");
        File[] files = dir.listFiles();
        MyFileList inFiles = new MyFileList();
        for(int i = 0; i < files.length; i++){

            MyFile myFile = new MyFile();
            BufferedImage image = ImageIO.read(files[i]);
            if(image == null){
                continue;
            }
            myFile.filename = files[i].getName();
            if (!setType(files[i], myFile)) continue;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, myFile.type, out);
            myFile.content = out.toByteArray();
            inFiles.files.add(myFile);

        }
        Gson gson = new Gson();
        String content = gson.toJson(inFiles, MyFileList.class);
        map.put("content", content);
        String result = HttpForTest.sendPost("http://localhost:8080/Servlet.UploadFile", map);

    }


}