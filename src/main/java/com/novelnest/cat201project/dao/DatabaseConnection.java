package com.novelnest.cat201project.dao;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Connection con = null;

        String host = "jdbc:oracle:thin:@db.freesql.com:1521/23ai_34ui2";
        String username = "ILLUSORYZEPHYR_SCHEMA_HJ8IH";
        String password = "QC5$wXLCYEWT554WLV1YYA7NJXNFWT";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(host, username, password);

        } catch (ClassNotFoundException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }

        return con;
    }
}