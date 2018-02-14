import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {

    public static List<Report> getReports(ReportTimes reportTimes)
            throws SQLException, ClassNotFoundException {

        List<Report> reports = new ArrayList<>();

        // Load JDBC Driver
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver Loaded");

        // Make connection to database
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/kindness", "java", "java");
        System.out.println("Database connected");

        Statement statement = connection.createStatement();
        ResultSet resultSet = null;


        /* Case for which statement to execute */
        switch (reportTimes) {
            case AllTime:
                resultSet = statement.executeQuery(
                        "select latitude, longitude from location");
                break;
            case LastDay:
                resultSet = statement.executeQuery(
                        "SELECT l.latitude " +
                            ",      l.longitude " +
                            "FROM   location l " +
                            "INNER JOIN report r ON " +
                            "    l.location_id = r.location_id " +
                            "WHERE r.report_date > DATE_SUB(NOW(), INTERVAL 24 HOUR)");
                break;
            case LastWeek:
                resultSet = statement.executeQuery(
                        "SELECT l.latitude " +
                            ",      l.longitude " +
                            "FROM   location l " +
                            "INNER JOIN report r ON " +
                            "    l.location_id = r.location_id " +
                            "WHERE r.report_date > DATE_SUB(NOW(), INTERVAL 7 DAY)");
                break;
            case LastMonth:
                resultSet = statement.executeQuery(
                        "SELECT l.latitude " +
                            ",      l.longitude " +
                            "FROM   location l " +
                            "INNER JOIN report r ON " +
                            "    l.location_id = r.location_id " +
                            "WHERE r.report_date > DATE_SUB(NOW(), INTERVAL 30 DAY)");
                break;
            case LastYear:
                resultSet = statement.executeQuery(
                        "SELECT l.latitude " +
                            ",      l.longitude " +
                            "FROM   location l " +
                            "INNER JOIN report r ON " +
                            "    l.location_id = r.location_id " +
                            "WHERE r.report_date > DATE_SUB(NOW(), INTERVAL 1 YEAR)");
                break;
        }


        while (resultSet.next()) {
            double latitude = Double.parseDouble(resultSet.getString(1));
            double longitude = Double.parseDouble(resultSet.getString(2));

            System.out.println(latitude + "\t" + longitude);

            Report report = new Report(latitude, longitude);
            reports.add(report);
        }

        connection.close();

        return reports;
    }
}
