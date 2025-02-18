import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AboutMe")
public class AboutMe extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Thông tin cá nhân</h2>");
        out.println("<p>Họ và tên: Trần Kim Quang</p>");
        out.println("<p>MSSV: 64131937</p>");
        out.println("<p>Chuyên ngành: Công nghệ thông tin</p>");
    }
}
