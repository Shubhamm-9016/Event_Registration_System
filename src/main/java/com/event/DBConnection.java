package com.event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Central place for the application's MySQL connection settings. */
public final class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/event_registration_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";       // Change if your MySQL username differs
    private static final String PASSWORD = "Hello@9016"; // Replace with your MySQL password

    private DBConnection() { }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver was not found. Check the Maven dependency.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
