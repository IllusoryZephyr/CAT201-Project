package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.Cart;
import com.novelnest.cat201project.models.CartItem;
import java.sql.*;

public class OrderDao {

    public int saveOrder(Cart cart, int userId, String address) {
        Connection con = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStock = null;
        ResultSet rs = null;
        int generatedOrderId = -1;

        try {
            con = DatabaseConnection.getConnection();

            con.setAutoCommit(false);

             String sqlOrder = "INSERT INTO ORDER_TB (USER_ID, TOTAL_AMOUNT, SHIPPING_ADDRESS, ORDER_STATUS) VALUES (?, ?, ?, 'Pending')";

            psOrder = con.prepareStatement(sqlOrder, new String[]{"ORDER_ID"});

            psOrder.setInt(1, userId);
            psOrder.setDouble(2, cart.grandTotal());
            psOrder.setString(3, address);

            int rowsAffected = psOrder.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // get the generated order id
            rs = psOrder.getGeneratedKeys();
            if (rs.next()) {
                generatedOrderId = rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

            //  save THE ORDER DETAILS
            String sqlDetail = "INSERT INTO ORDER_DETAILS_TB (ORDER_ID, BOOK_ID, QUANTITY, PRICE_AT_PURCHASE) VALUES (?, ?, ?, ?)";
            psDetail = con.prepareStatement(sqlDetail);

            String sqlUpdateStock = "UPDATE BOOK_TB SET BOOK_QUANTITY = BOOK_QUANTITY - ? WHERE BOOK_ID = ?";
            psUpdateStock = con.prepareStatement(sqlUpdateStock);

            for (CartItem item : cart.getItems()) {
                psDetail.setInt(1, generatedOrderId);
                psDetail.setInt(2, item.getBook().getId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setDouble(4, item.getBook().getPrice());

                psDetail.addBatch();

                // Add stock update to batch
                psUpdateStock.setInt(1, item.getQuantity());
                psUpdateStock.setInt(2, item.getBook().getId());
                psUpdateStock.addBatch();
            }

            psDetail.executeBatch();
            psUpdateStock.executeBatch();

            //  save everything(commit)
            con.commit();
            System.out.println("Order saved successfully! ID: " + generatedOrderId);

        } catch (SQLException e) {
            e.printStackTrace(); //print report of the method call that leads to error

            try {
                if (con != null) {
                    con.rollback(); //undo all change
                    System.out.println("Transaction rolled back due to error.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            generatedOrderId = -1;
        } finally {
            // Close resources to prevent memory leaks
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (psOrder != null) psOrder.close(); } catch (Exception e) {}
            try { if (psDetail != null) psDetail.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
            try { if (psUpdateStock != null) psUpdateStock.close(); } catch (Exception e) {}
        }

        return generatedOrderId;
    }
}