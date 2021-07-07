package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    public static  Logger logger = Logger.getLogger("Logger");
    private final static String username = "root";
    private final static String password = "belka20";
    private final static String dbUrl = "jdbc:mysql://localhost:3306/world?verifyServerCertificate=false&useSSL=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static void showError(SQLException e) {
        logger.severe("Error: " + e.getMessage());
        logger.severe("Error Code: " + e.getErrorCode());
    }
}
