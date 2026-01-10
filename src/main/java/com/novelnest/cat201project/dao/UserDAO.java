package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.UserInfo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean addUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();

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

                        return true;
                    }
                }
            }
        }

        catch (SQLException _) {}

        return false;
    }

    public boolean editUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();

            String sqlCommand = "UPDATE USER_TB SET USER_NAME = ?, USER_PASSWORD = ?, USER_IS_ADMIN = ? WHERE USER_ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setBoolean(3, user.isAdmin());
            pstmt.setInt(4, user.getId());

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        }

        catch (SQLException _) {}

        return false;
    }

    public boolean deleteUser(UserInfo user){
        try {
            Connection con = DatabaseConnection.getConnection();

            String sqlCommand = "DELETE FROM USER_TB WHERE USER_ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setInt(1, user.getId());

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        }

        catch (SQLException _) {}

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

        catch (SQLException _) {}

        return false;
    }

    public List<UserInfo> getAllUser() {
        List<UserInfo> users = new ArrayList<>();

        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "SELECT * FROM USER_TB";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                LocalDateTime created = rs.getTimestamp("user_creation_date").toLocalDateTime();
                boolean admin = rs.getBoolean("user_is_admin");

                UserInfo user = new UserInfo(id, name, password, created, admin);
                users.add(user);
            }
        }

        catch (SQLException _) {}

        return users;
    }

    public UserInfo getUserById(int id){
        UserInfo user = new UserInfo();
        user.setId(id);

        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "SELECT USER_NAME, USER_PASSWORD, USER_CREATION_DATE, USER_IS_ADMIN FROM USER_TB WHERE USER_ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setString(1, String.valueOf(id));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user.setName(rs.getString("USER_NAME"));
                user.setPassword(rs.getString("USER_PASSWORD"));
                user.setCreated(rs.getTimestamp("USER_CREATION_DATE").toLocalDateTime());
                user.setAdmin(rs.getBoolean("USER_IS_ADMIN"));

                return user;
            }
        }

        catch (SQLException _) {}

        return user;
    }
}