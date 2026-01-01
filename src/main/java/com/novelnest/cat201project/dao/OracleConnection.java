package com.novelnest.cat201project.dao;
import java.sql.*;

public class OracleConnection {
    public static void main(String[] args) {
        String host = "jdbc:oracle:thin:@db.freesql.com:1521/23ai_34ui2";
        String username = "ILLUSORYZEPHYR_SCHEMA_HJ8IH";
        String password = "RGN5U82DD97JICRINGUI4ZA$TCWs5E";
        try {
            Connection conn = DriverManager.getConnection(host, username, password);
            System.out.println("Connected to Oracle database");
        } catch (SQLException e) {
            System.out.println("Failed to connect to Oracle database");
            e.printStackTrace();
        }
    }
}