import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BMI")
public class BMI extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<form action='BMI' method='post'>");
        out.println("Chiều cao (m): <input type='text' name='height'><br>");
        out.println("Cân nặng (kg): <input type='text' name='weight'><br>");
        out.println("<input type='submit' value='Tính BMI'>");
        out.println("</form>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        double height = Double.parseDouble(request.getParameter("height"));
        double weight = Double.parseDouble(request.getParameter("weight"));
        double bmi = weight / (height * height);

        out.println("<h2>Kết quả BMI</h2>");
        out.println("<p>BMI của bạn: " + bmi + "</p>");

        if (bmi < 18.5) {
            out.println("<p>Xếp loại: Gầy (ốm)</p>");
        } else if (bmi < 25) {
            out.println("<p>Xếp loại: Bình thường</p>");
        } else if (bmi < 30) {
            out.println("<p>Xếp loại: Thừa cân</p>");
        } else {
            out.println("<p>Xếp loại: Béo phì</p>");
        }
    }
}
