import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/KindnessServlet")
public class KindnessServlet extends HttpServlet {

    public KindnessServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            /* read what it's getting */
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count += c;
            }
            sin.close();

            String receivedString = new String(input);
            response.setStatus(HttpServletResponse.SC_OK);

            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

            List<Report> reports = DBConnector.getReports(ReportTimes.AllTime);

            for (Report report : reports) {
                writer.write(report.toString());
                writer.flush();
            }
            writer.close();

        } catch (Exception e) {
            try {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (Exception e2) {

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getOutputStream().println("This servlet is working just fine.");
    }
}
