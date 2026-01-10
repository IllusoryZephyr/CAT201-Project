package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.MessageInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerServiceDAO {

    public boolean addMessage(MessageInfo message) {
        try {
            Connection con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlCommand = "INSERT INTO MESSAGE_TB (MESSAGE, SENDER) VALUES (?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sqlCommand);

            pstmt.setString(1, message.getMessage());
            pstmt.setString(2, message.getSender());

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
}
