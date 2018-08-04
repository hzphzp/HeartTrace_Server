package Servlet;

import Json.MyFile;
import Json.MyFileList;
import Jwt.JavaWebToken;
import Output.Output;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
@MultipartConfig
@WebServlet(name = "Servlet.Loginup")
public class UploadFile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        JavaWebToken.verifyToken(token, username, modelnum);
        String content = request.getParameter("content");
        Long anchor = Long.parseLong(request.getParameter("anchor"));
        Gson gson = new Gson();
        MyFileList inFile = gson.fromJson(content, MyFileList.class);
        MyFileList outFile = new MyFileList();
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + username;
        File dir = new File(uploadPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File[] fileback = dir.listFiles();
        //处理现在服务器上的图片文件
        for(int i = 0; i < fileback.length; i++) {
            if (fileback[i].lastModified() > anchor) {
                MyFile myFile = new MyFile();
                BufferedImage image = ImageIO.read(fileback[i]);
                if (image == null) {
                    continue;
                }
                myFile.filename = fileback[i].getName();
                if (!setType(fileback[i], myFile)) continue;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(image, myFile.type, out);
                myFile.content = out.toByteArray();
                outFile.files.add(myFile);
            }
        }
        //确定了要返回的图片
        log("huangzp");
        if(inFile.files != null) {
            for (MyFile myFile : inFile.files) {
                //处理客户端的发过来的图片文件
                if(myFile.type.equals("jpg") || myFile.type.equals("png") || myFile.type.equals("gif")){
                    File fileStore = new File(dir + File.separator + myFile.filename);
                    fileStore.createNewFile();
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(myFile.content));
                    ImageIO.write(image, myFile.type, fileStore);
                }
            }
        }
        String json = gson.toJson(outFile);
        Output.output(json, response);
    }



    static boolean setType(File file, MyFile myFile) {
        if(file.getName().endsWith(".gif")){
                myFile.type = "gif";
        }
        else if (file.getName().endsWith(".png")){
            myFile.type = "png";
        }
        else if (file.getName().endsWith(".jpg")){
            myFile.type = "jpg";
        }
        else {
            return false;
        }
        return true;
    }
}
