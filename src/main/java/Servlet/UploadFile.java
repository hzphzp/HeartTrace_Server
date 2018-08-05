package Servlet;

import Json.MyFile;
import Json.MyFileList;
import Jwt.JavaWebToken;
import Output.Output;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
@MultipartConfig
@WebServlet(name = "Servlet.Loginup")
public class UploadFile extends HttpServlet {

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
                myFile.filename = fileback[i].getName();
                /*ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(image, myFile.type, out);
                myFile.content = out.toByteArray();*/
                FileInputStream fin = new FileInputStream(fileback[i]);
                InputStreamReader reader = new InputStreamReader(fin);
                BufferedReader buffReader = new BufferedReader(reader);
                String strTmp;
                while((strTmp = buffReader.readLine())!=null){
                    myFile.content += strTmp;
                    myFile.content += "\n";
                }
                myFile.content = myFile.content.substring(0, myFile.content.length()-1);
                buffReader.close();

                outFile.files.add(myFile);
            }
        }
        //确定了要返回的图片
        log("huangzp");
        if(inFile.files != null) {
            for (MyFile myFile : inFile.files) {
                //处理客户端的发过来的图片文件
                File fileStore = new File(dir + File.separator + myFile.filename);
                fileStore.createNewFile();
                /*BufferedImage image = ImageIO.read(new ByteArrayInputStream(myFile.content));
                ImageIO.write(image, myFile.type, fileStore);*/
                FileWriter fw = new FileWriter(fileStore);
                fw.write(myFile.content);
                fw.close();
            }
        }
        String json = gson.toJson(outFile);
        Output.output(json, response);
    }



    /*static boolean setType(File file, MyFile myFile) {
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
    }*/
}
