package com.novelnest.cat201project.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class OracleConnection {
    public static void main(String[] args) {// Load the .env file
        Dotenv dotenv = Dotenv.load();

        String host = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        try {
            Connection conn = DriverManager.getConnection(host, username, password);
            System.out.println("Connected to Oracle database");
        } catch (SQLException e) {
            System.out.println("Failed to connect to Oracle database");
            e.printStackTrace();
        }
    }
}