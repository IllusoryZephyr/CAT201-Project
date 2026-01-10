package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.UserInfo;

import java.sql.*;

public class UserDAO {

    public boolean addUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "INSERT INTO USER_TB (USER_NAME, USER_PASSWORD) VALUES (?, ?)";
            String[] returncols = {"USER_ID", "USER_CREATION_DATE"};

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

    public boolean editUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "UPDATE USER_TB SET USER_NAME = ?, USER_PASSWORD = ?, USER_IS_ADMIN = ? WHERE USER_ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setBoolean(3, user.isAdmin());
            pstmt.setInt(4, user.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                con.commit();
                return true;
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

    public boolean deleteUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "DELETE FROM USER_TB WHERE USER_ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setInt(1, user.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                con.commit();
                return true;
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

    public boolean checkUserPassword(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "SELECT USER_ID, USER_CREATION_DATE, USER_IS_ADMIN FROM USER_TB WHERE USER_NAME = ? AND USER_PASSWORD = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("USER_ID"));
                user.setCreated(rs.getTimestamp("USER_CREATION_DATE").toLocalDateTime());
                user.setAdmin(rs.getBoolean("USER_IS_ADMIN"));

                return true;
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