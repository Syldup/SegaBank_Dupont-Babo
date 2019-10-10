package dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PersistenceManager {
    private static Connection conn;

    public static Connection getConn() throws SQLException, IOException {
        if (conn == null || conn.isClosed() || conn.isValid(5))
            try (InputStream input = new FileInputStream(".\\resources\\config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                conn = DriverManager.getConnection(prop.getProperty("db.url"),
                        prop.getProperty("db.user"), prop.getProperty("db.password"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return conn;
    }

    public static void closeConn() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
