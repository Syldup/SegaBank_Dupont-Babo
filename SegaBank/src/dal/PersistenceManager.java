package dal;

import java.io.FileInputStream;
import java.io.IOException;
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
        if (conn == null || conn.isClosed() || conn.isValid(5)) {
            Path path = Paths.get(".\\db.properties");
            FileInputStream fis = (FileInputStream) Files.newInputStream(path);
            Properties props = new Properties();
            props.load(fis);
            conn = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_LOGIN"), props.getProperty("DB_PASSWORD"));
        }
        return conn;
    }

    public static void closeConn() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
