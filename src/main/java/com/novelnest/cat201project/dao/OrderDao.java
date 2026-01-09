package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.Cart;
import com.novelnest.cat201project.models.CartItem;
import java.sql.*;

public class OrderDao {

    public int saveOrder(Cart cart, int userId, String address) {
        Connection con = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        ResultSet rs = null;
        int generatedOrderId = -1; //default.-1 mean no order created

        try {
            con = DatabaseConnection.getConnection();

            // turn off auto save
            // This ensures that if saving details fails, the order is cancelled.
            con.setAutoCommit(false);

            // STEP A: save THE MAIN ORDER
            // Order ID is not inserted because it is automatically generated already
            //(old version) String sqlOrder = "INSERT INTO ORDERS (USER_ID, TOTAL_AMOUNT, SHIPPING_ADDRESS, ORDER_STATUS) VALUES (?, ?, ?, 'Pending')";
            String sqlOrder = "INSERT INTO ORDERS_TB (ORDER_ID, USER_ID, TOTAL_AMOUNT, SHIPPING_ADDRESS, ORDER_STATUS) VALUES (ORDER_ID_SEQ.NEXTVAL, ?, ?, ?, 'Pending')";

            //  "new String[]{"ORDER_ID"}" tells Oracle to return the generated value of ORDER_ID
            psOrder = con.prepareStatement(sqlOrder, new String[]{"ORDER_ID"}); //"ORDER_ID" is the name of the primary key column

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
                generatedOrderId = rs.getInt(1); // This is the ID from the Sequence
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

            //  save THE ORDER DETAILS
            String sqlDetail = "INSERT INTO ORDER_DETAILS_TB (ORDER_ID, BOOK_ID, QUANTITY, PRICE_AT_PURCHASE) VALUES (?, ?, ?, ?)";
            psDetail = con.prepareStatement(sqlDetail);

            for (CartItem item : cart.getItems()) {
                psDetail.setInt(1, generatedOrderId);       // Use the ID we just got
                psDetail.setInt(2, item.getBook().getId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setDouble(4, item.getBook().getPrice());

                psDetail.addBatch(); // Queue them first
            }

            // Execute all detail inserts at once
            psDetail.executeBatch();

            //  save everything(commit)
            con.commit();
            System.out.println("Order saved successfully! ID: " + generatedOrderId);

        } catch (SQLException e) {
            e.printStackTrace(); //print report of the method call that leads to error
            // ROLLBACK: Undo everything if there was an error
            try {
                if (con != null) {
                    con.rollback(); //undo all change
                    System.out.println("Transaction rolled back due to error.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Close resources to prevent memory leaks
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (psOrder != null) psOrder.close(); } catch (Exception e) {}
            try { if (psDetail != null) psDetail.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return generatedOrderId; //return order id
    }
}