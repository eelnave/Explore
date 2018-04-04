
// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class WorldServlet extends HttpServlet{
    // The doGet() runs once per HTTP GET request to this servlet.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the MIME type for the response message
        response.setContentType("text/html");
        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;
        try {
            // Step 1: Allocate a database Connection object
            Class.forName("com.mysql.jdbc.Driver");  // Needed for JDK9/Tomcat9
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/kindness?useSSL=false", "root", "4321"); // <== Check!
            // database-URL(hostname, port, default database), username, password

            // Step 2: Allocate a Statement object within the Connection
            stmt = conn.createStatement();

            // Step 3: Execute a SQL SELECT query
            String sqlStr = "select * from kind_table";

            // Print an HTML page as the output of the query
            out.println("<html><head><title>World Kindness</title></head><body>");
            out.println("<h3>World Kindness.</h3>");
            out.println("<p>Your query is: " + sqlStr + "</p>"); // Echo for debugging
            ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

            // Step 4: Process the query result set
            int count = 0;
            out.println("<table style='width:100%;'>" +
                    "<tr><th>ID</th><th>Longitude</th><th>Latitude</th><th>Category</th><th>Date</th></tr>"
            );
            while(rset.next()) {
                // Print a paragraph <p>...</p> for each record
                out.println("<tr style='text-align:center;'>"
                        + "<td>" + rset.getString("id") + "</td>"
                        + "<td>" + rset.getString("longitude") + "</td>"
                        + "<td>" + rset.getString("latitude") + "</td>"
                        + "<td>" + rset.getString("category") + "</td>"
                        + "<td>" + rset.getString("date") + "</td>" +"</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<a href='http://localhost:9999'>Go Back</a></body></html>");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            out.close();  // Close the output writer
            try {
                // Step 5: Close the resources
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
