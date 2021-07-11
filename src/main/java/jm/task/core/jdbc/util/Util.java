package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static SessionFactory sessionFactory;
    public static Logger logger = Logger.getLogger("Logger");
    private final static String username = "root";
    private final static String password = "belka20";
    private final static String dbUrl = "jdbc:mysql://localhost:3306/world?verifyServerCertificate=false&useSSL=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, username);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void showError(SQLException e) {
        logger.severe("Error: " + e.getMessage());
        logger.severe("Error Code: " + e.getErrorCode());
    }


}
