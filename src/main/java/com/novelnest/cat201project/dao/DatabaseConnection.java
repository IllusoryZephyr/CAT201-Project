/*
-----------------For MySql------------------
package com.novelnest.cat201project.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;


public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        Connection con = null;

        Dotenv dotenv = Dotenv.load();

        // Ensure these match your .env keys
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASS");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return con; // Returns the connection (or null if failed)

    }
}
*/
//--------------For Oracle-----------------------
package com.novelnest.cat201project.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        Connection con = null;

        Dotenv dotenv = Dotenv.load();

        // Ensure these match your .env keys
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASS");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return con; // Returns the connection (or null if failed)

    }
}