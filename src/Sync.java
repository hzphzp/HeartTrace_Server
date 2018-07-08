import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Sync")
public class Sync extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("table");
        String jsonData = request.getParameter("data");

        Gson gson = new GsonBuilder().create();
        java.lang.reflect.Type classType = new TypeToken<Diaries>() {}.getType();

        Diaries diaries = gson.fromJson(jsonData.toString(), classType);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String jsonData2 = gson.toJson(diaries);
        jsonData2 = jsonData2.toString();
        writer.print(jsonData2);
        writer.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
