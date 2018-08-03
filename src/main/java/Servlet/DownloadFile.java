package Servlet;

import Jwt.JavaWebToken;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "Servlet.DownloadFile")
public class DownloadFile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String token = request.getParameter("token");
        String modelnum = request.getParameter("modelnum");
        JavaWebToken.verifyToken(token, username, modelnum);
        String filename = request.getParameter("filename");
        isMultipart(request, response);
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + username;
        // 设置响应头
        response.setContentType(getServletContext().getMimeType(filename));// 根据文件扩展名获得MIME类型
        // 等级于 response.setHeader("Content-Type",xxx);
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);// 以附件下载

        InputStream in = new BufferedInputStream(new FileInputStream(
                uploadPath+filename));
        // 需要浏览器输出流
        OutputStream out = response.getOutputStream();
        int temp;
        while ((temp = in.read()) != -1) {
            out.write(temp);
        }
        out.close();
        in.close();

   }

    static void isMultipart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
    }
}
