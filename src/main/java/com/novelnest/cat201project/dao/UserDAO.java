package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.UserInfo;

import java.sql.*;

public class UserDAO {

    public boolean addUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "INSERT INTO USERS (USER_NAME, USER_PASSWORD) VALUES (?, ?)";
            String[] returncols = {"USER_ID", "USER_CREATE_DATE"};

            PreparedStatement pstmt = con.prepareStatement(sqlCommand, returncols);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                        user.setCreated(generatedKeys.getTimestamp(2).toLocalDateTime());

                        con.commit();
                        return true;
                    }
                }
            }
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                return false;
            }

            throw new RuntimeException(e);
        }

        return false;
    }
}