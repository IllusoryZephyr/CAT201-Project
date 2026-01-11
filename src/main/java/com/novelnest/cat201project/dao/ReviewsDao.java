package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.Reviews; // You need a Review model class
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDao {

    public boolean addReview(Reviews review) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        String sql = "INSERT INTO REVIEW_TB (USER_ID, BOOK_ID, REVIEW_TITLE, REVIEW_RATING, REVIEW_DESCRIPTION, REVIEW_CREATION_DATE) VALUES (?, ?, ?, ?, ?, SYSTIMESTAMP)";
        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getBookId());

            String title = (review.getTitle() != null) ? review.getTitle() : "Review";
            ps.setString(3, title);
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getDescription());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                success = true;
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return success;
    }
    // read review according to bookID
    public List<Reviews> getReviewsByBookId(int bookId) {
        List<Reviews> reviews = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM REVIEW_TB WHERE BOOK_ID = ? ORDER BY REVIEW_CREATION_DATE DESC";

        try {
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Reviews r = new Reviews();
                r.setUserId(rs.getInt("USER_ID"));
                r.setBookId(rs.getInt("BOOK_ID"));
                r.setTitle(rs.getString("REVIEW_TITLE"));
                r.setRating(rs.getInt("REVIEW_RATING"));
                r.setDescription(rs.getString("REVIEW_DESCRIPTION"));
                r.setCreateDate(rs.getTimestamp("REVIEW_CREATION_DATE"));
                reviews.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return reviews;
    }
    public double getAverageRating(int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        double average = 0.0;

        String sql = "SELECT AVG(REVIEW_RATING) FROM REVIEW_TB WHERE BOOK_ID = ?";
        try {
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                average = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Oracle Average Rating Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return average;
    }

}