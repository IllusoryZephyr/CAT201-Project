package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.BookInfo;
import com.novelnest.cat201project.dao.DatabaseConnection;
import com.novelnest.cat201project.models.Reviews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDao {
    public boolean addReview(Reviews review) {
        String query = "INSERT INTO REVIEWS (USER_ID, BOOK_ID, REVIEW_TITLE, REVIEW_RATING, REVIEW_DESCRIPTION) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getBookId());
            ps.setString(3, review.getTitle());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getDescription());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reviews> getReviewsByBookId(int bookId) {
        List<Reviews> reviewsList = new ArrayList<>();
        // SQL to fetch reviews for a specific book, newest first
        String query = "SELECT * FROM REVIEWS WHERE BOOK_ID = ? ORDER BY REVIEW_CREATE_DATE DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reviews review = new Reviews();
                review.setUserId(rs.getInt("USER_ID"));
                review.setBookId(rs.getInt("BOOK_ID"));
                review.setTitle(rs.getString("REVIEW_TITLE"));
                review.setRating(rs.getInt("REVIEW_RATING"));
                review.setDescription(rs.getString("REVIEW_DESCRIPTION"));
                review.setCreateDate(rs.getTimestamp("REVIEW_CREATE_DATE"));
                reviewsList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewsList;
    }

    public double getAverageRating(int bookId) {
        double average = 0.0;
        String query = "SELECT AVG(REVIEW_RATING) as avg_rating FROM REVIEWS WHERE BOOK_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                average = rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Returns 0.0 if no reviews exist
        return Math.round(average * 10.0) / 10.0; // Rounds to 1 decimal place (e.g., 4.5)
    }
}
