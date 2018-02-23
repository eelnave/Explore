import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/KindnessServlet")
public class KindnessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KindnessServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getOutputStream().println("Hurray !! This Servlet Works");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			/* Not exactly sure what this section does */
			/* I just know it does not work without it */
			int length = request.getContentLength();
			byte[] input = new byte[length];
			ServletInputStream sin = request.getInputStream();
			int c, count = 0 ;
			while ((c = sin.read(input, count, input.length-count)) != -1) {
				count +=c;
			}
			sin.close();

			/* Store input from client into a variable */
			String receivedString = new String(input);

			/* Not sure what this means */
			response.setStatus(HttpServletResponse.SC_OK);

			String[] userRequest = receivedString.split(",");

			if(userRequest[0].equals("select")) {
				/* populate list of reports */
				List<Report> reports = DBConnector.getReports(ReportTimes.AllTime);

				/* Create a writer to write back to the client */
				OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

				for (Report report : reports) {
					writer.write(report.toString() + "\n");
				}

				writer.flush();
				writer.close();
			}
			else if (userRequest[0].equals("insert")) {
				double latitude = Double.parseDouble(userRequest[1]);
				double longitude = Double.parseDouble(userRequest[2]);
				DBConnector.addReport(latitude, longitude);
			}

		} catch (IOException e) {
			try{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException ioe) {
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}