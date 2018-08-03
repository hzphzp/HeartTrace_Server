package ClassForTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Uploader {
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    public static boolean uploadFiles(String url, Map<String, String> params, List<File> files) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);//创建 HTTP POST 请求
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        //builder.setCharset(Charset.forName("utf-8"));//设置请求的编码格式
        for (File file : files) {
            builder.addBinaryBody(file.getName(), file);
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            builder.addTextBody(entry.getKey(), entry.getValue());
        }
        HttpEntity entity = builder.build();// 生成 HTTP POST 实体
        post.setEntity(entity);//设置请求参数
        CloseableHttpResponse response = httpclient.execute(post);// 发起请求 并返回请求的响应
        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        }
        return false;
    }
}
