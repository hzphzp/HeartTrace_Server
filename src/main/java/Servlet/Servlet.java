package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

@WebServlet(name = "Servlet.Servlet")
public class Servlet extends HttpServlet {

    public void init() throws ServletException{

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setIntHeader("Refresh", 5);
        Date date = new Date();
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String massage = request.getParameter("massage");
        String order = request.getParameter("order");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        writer.println(
                "<h1>"+ massage + order + id + action +"</h1>"
        );
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String paraName = (String) headerNames.nextElement();
            writer.println("<tr><td>" + paraName + "</td>\n");
            String paraValue = request.getHeader(paraName);
            writer.println("<td>" + paraValue + "</td></tr>\n");
            writer.println("<h1>" + date.toString() + "</h1>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy(){

    }
}
