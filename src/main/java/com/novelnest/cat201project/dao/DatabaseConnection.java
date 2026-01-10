package com.novelnest.cat201project.dao;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Connection con = null;

        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "hr";
        String password = "hr";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return con;

    }
}