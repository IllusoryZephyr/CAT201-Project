package com.novelnest.cat201project.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. Create a static method that returns a Connection object
    public static Connection getConnection() {
        Connection con = null;

        // Load credentials from .env
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASS");

        try {
            // Optional: Explicitly load the Oracle driver (good safety measure)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }

        return con; // Returns the connection (or null if failed)
    }

    // 2.main() for quick testing
    public static void main(String[] args) {
        Connection con = DatabaseConnection.getConnection();
        if (con != null) {
            System.out.println("Success! Connected to database.");
        } else {
            System.out.println("Failed to connect.");
        }
    }
}