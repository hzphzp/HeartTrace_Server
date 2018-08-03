package Servlet;

import ClassForTest.Uploader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
        map.put("anchor", "1533227264763");
        File[] fileList = new File("D:\\Pictures").listFiles();
        List<File> files = Arrays.asList(fileList);
        Uploader.uploadFiles("http://localhost:8080/Servlet.UploadFile", map, files);

    }
}