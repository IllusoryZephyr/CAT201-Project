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
            pstmt.setBoolean(4, message.getOwnerSeen());
            pstmt.setBoolean(5, message.getAdminSeen());

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

                while (rs.next()) {
                    storeMessageInList(messageList, rs);
                }
                
                messageList.sort(Comparator.comparing(MessageInfo::getTimestamp));
            }
        }
        catch (SQLException _) {}

        return messageList;
    }

    public List<List<MessageInfo>> getAllMessages() {
        List<List<MessageInfo>> chatList = new ArrayList<>();
        String sqlCommand = "SELECT m.OWNER_ID, m.SENDER_ID, m.TIMESTAMP, m.MESSAGE, m.OWNER_SEEN, m.ADMIN_SEEN " + 
                                "FROM USER_TB u " +
                                "JOIN MESSAGE_TB m ON u.USER_ID = m.OWNER_ID " +
                                "WHERE u.USER_IS_ADMIN = 0 " +
                                "ORDER BY m.OWNER_ID, m.TIMESTAMP ASC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sqlCommand);
             ResultSet rs = pstmt.executeQuery()) {

            int lastUserId = -1;
            List<MessageInfo> currentMessageList = null;

            while (rs.next()) {
                int currentUserId = rs.getInt("OWNER_ID");

                if (currentUserId != lastUserId) {
                    currentMessageList = new ArrayList<>();
                    chatList.add(currentMessageList);
                    lastUserId = currentUserId;
                }

                if (currentMessageList != null) storeMessageInList(currentMessageList, rs);
            }
        }
        catch (SQLException _) {}

        return chatList;
    }
    
    private void storeMessageInList(List<MessageInfo> messageList, ResultSet rs) throws SQLException {
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
