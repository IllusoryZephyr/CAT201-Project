package com.novelnest.cat201project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDao {

    public boolean savePayment(int orderId, String paymentMethod) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean isSuccess = false;

        try {
            con = DatabaseConnection.getConnection();

            // Insert into the PAYMENTS table
            String sql = "INSERT INTO PAYMENT_TB (ORDER_ID, PAYMENT_METHOD, TRANSACTION_DATE) VALUES (?, ?, CURRENT_TIMESTAMP)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setString(2, paymentMethod);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return isSuccess;
    }
}