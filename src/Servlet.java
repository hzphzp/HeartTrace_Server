import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    private String massage;
    private String order;
    private String id;
    private String action;

    public void init() throws ServletException{

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        massage = request.getParameter("massage");
        order = request.getParameter("order");
        id = request.getParameter("id");
        action = request.getParameter("action");
        writer.println(
                "<h1>"+massage+order+id+action+"</h1>"
        );
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy(){

    }
}
