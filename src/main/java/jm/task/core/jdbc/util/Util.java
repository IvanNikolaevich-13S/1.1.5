package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.security.auth.login.AppConfigurationEntry;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final Connection connection;
    private static final SessionFactory sessionFactory;

    private static final String URL = "jdbc:mysql://localhost:3306/firstdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    static {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    static {
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static Connection getConnection() {
        return connection;
    }
}
