package org.andersen.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("liquibase.properties")) {
            if (input == null) {
                throw new IOException();
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Connection getConnection() throws SQLException {
        String url = getProperty("url");
        String username = getProperty("username");
        String password = getProperty("password");
        String driver = getProperty("driver");

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException();
        }
    }
}
