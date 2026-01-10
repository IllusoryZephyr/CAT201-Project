package com.novelnest.cat201project.dao;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Connection con = null;

        String url = "jdbc:oracle:thin:@db.freesql.com:1521/23ai_34ui2";
        String user = "ILLUSORYZEPHYR_SCHEMA_HJ8IH";
        String password = "7BQAGQWYEVTL46B7QWWx!HTEA0QJHG";

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