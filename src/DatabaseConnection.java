import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	final static String URL = "jdbc:mysql://localhost:3306/stocks?serverTimezone=UTC";
    final static String LOGIN = "root";
    final static String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASS);
    }
}
