package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.MessageInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerServiceDAO {

    public boolean addMessage(MessageInfo message) {
        String sqlCommand = "INSERT INTO MESSAGE_TB (OWNER_ID, SENDER_ID, MESSAGE, OWNER_SEEN, ADMIN_SEEN) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sqlCommand)){

            pstmt.setString(1, String.valueOf(message.getOwnerID()));
            pstmt.setString(2, String.valueOf(message.getSenderID()));
            pstmt.setString(3, message.getMessage());
            pstmt.setString(4, String.valueOf(message.getOwnerSeen()));
            pstmt.setString(5, String.valueOf(message.getAdminSeen()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {return true;}
        }
        catch (SQLException _) {}

        return false;
    }

    public List<MessageInfo> getMessagesByOwnerID(int ownerID) {
        List<MessageInfo> messageList = new ArrayList<>();
        String sqlCommand = "SELECT * FROM MESSAGE_TB WHERE OWNER_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sqlCommand)){

            pstmt.setString(1, String.valueOf(ownerID));

            try (ResultSet rs = pstmt.executeQuery()) {

                storeMessageList(messageList, rs);
                messageList.sort(Comparator.comparing(MessageInfo::getTimestamp));

                return messageList;
            }
        }
        catch (SQLException _) {}

        return messageList;
    }

    public List<List<MessageInfo>> getAllMessages() {
        List<List<MessageInfo>> chatList = new ArrayList<>();
        String sqlCommand = "SELECT USER_ID FROM USER_TB WHERE USER_IS_ADMIN = 0";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sqlCommand);
             ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {
                List<MessageInfo> messageList = new ArrayList<>();
                String sqlCommand2 = "SELECT * FROM MESSAGE_TB WHERE OWNER_ID = ?";

                try (PreparedStatement pstmt2 = con.prepareStatement(sqlCommand2)) {

                    pstmt2.setString(1, String.valueOf(rs.getInt("USER_ID")));

                    try (ResultSet rs2 = pstmt2.executeQuery()){

                        storeMessageList(messageList, rs2);
                        messageList.sort(Comparator.comparing(MessageInfo::getTimestamp));

                        chatList.add(messageList);
                    }
                }
            }
        }
        catch (SQLException _) {}

        return chatList;
    }

    private void storeMessageList(List<MessageInfo> messageList, ResultSet rs) throws SQLException {
        while (rs.next()) {
            MessageInfo message = new MessageInfo();

            message.setOwnerID(rs.getInt("OWNER_ID"));
            message.setSenderID(rs.getInt("SENDER_ID"));
            message.setTimestamp(rs.getTimestamp("TIMESTAMP").toLocalDateTime());
            message.setMessage(rs.getString("MESSAGE"));
            message.setOwnerSeen(rs.getBoolean("OWNER_SEEN"));
            message.setAdminSeen(rs.getBoolean("ADMIN_SEEN"));

            messageList.add(message);
        }
    }
}
