package Servlet;

import Jwt.JavaWebToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet.Testverify")
public class Testverify extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernmae = request.getParameter("username");
        String modelnum = request.getParameter("modelnum");
        String token = request.getParameter("token");
        JavaWebToken.verifyToken(token, usernmae, modelnum);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
