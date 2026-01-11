package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.BookInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 1. CREATE
    public int addBook(BookInfo book) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = -1;

        String sql = "INSERT INTO BOOK_TB (BOOK_ID, BOOK_TITLE, BOOK_AUTHOR, BOOK_CATEGORY, BOOK_DESCRIPTION, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH) " +
                "VALUES (BOOK_ID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql, new String[]{"BOOK_ID"});

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getSynopsis()); // Maps to BOOK_DESCRIPTION
            ps.setDouble(5, book.getPrice());
            ps.setInt(6, book.getQuantity());
            ps.setString(7, book.getImagePath());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            closeResources(rs, ps, con); // Use helper for consistency
        }
        return generatedId;
    }

    // 2. READ ALL
    public List<BookInfo> getAllBooks() {
        List<BookInfo> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM BOOK_TB ORDER BY BOOK_ID";

        try {
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return books;
    }

    // 3. READ ONE (Fixed Syntax Error)
    public BookInfo getBookById(int id) {
        BookInfo book = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM BOOK_TB WHERE BOOK_ID = ?";

        try {
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                // FIXED: Direct assignment using helper method
                book = mapResultSetToBook(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con); // Use helper for consistency
        }
        return book;
    }

    // 4. UPDATE
    public boolean updateBook(BookInfo book) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        String sql = "UPDATE BOOK_TB SET BOOK_TITLE = ?, BOOK_AUTHOR=?, BOOK_CATEGORY=?, BOOK_DESCRIPTION = ?, BOOK_PRICE = ?, BOOK_QUANTITY = ?, BOOK_IMAGE_PATH = ? WHERE BOOK_ID = ?";

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getSynopsis());
            ps.setDouble(5, book.getPrice());
            ps.setInt(6, book.getQuantity());
            ps.setString(7, book.getImagePath());
            ps.setInt(8, book.getId());

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
            closeResources(null, ps, con);
        }
        return success;
    }

    public boolean deleteBook(int id) {
        Connection con = null;
        PreparedStatement psBook = null;
        PreparedStatement psReview = null;
        boolean isDeleted = false;

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false); // 1. Start Transaction

            // ---------------------------------------------------------
            // STEP A: Delete Reviews (Check table name!)
            // If your book table is HR.BOOK_TB, your review table is likely HR.REVIEWS or HR.REVIEW_TB
            // ---------------------------------------------------------
            String deleteReviewsSql = "DELETE FROM REVIEWS WHERE BOOK_ID = ?";
            psReview = con.prepareStatement(deleteReviewsSql);
            psReview.setInt(1, id);
            psReview.executeUpdate();

            // ---------------------------------------------------------
            // STEP B: Delete Book (MUST MATCH YOUR getAllBooks NAME)
            // Fixed: Added "HR." prefix
            // ---------------------------------------------------------
            String deleteBookSql = "DELETE FROM BOOK_TB WHERE BOOK_ID = ?";
            psBook = con.prepareStatement(deleteBookSql);
            psBook.setInt(1, id);

            int rowsAffected = psBook.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true;
                con.commit(); // 2. Commit if successful
                System.out.println("DEBUG: Book deleted successfully.");
            } else {
                con.rollback(); // Rollback if book not found
                System.out.println("DEBUG: Book ID not found.");
            }

        } catch (SQLException e) {
            // 3. Rollback on Error
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}

            System.out.println("DEBUG ERROR: " + e.getMessage());
            e.printStackTrace(); // Check this if it still fails!
        } finally {
            // 4. Close Resources
            // Since you have a helper method 'closeResources', you can use it here too:
            // closeResources(null, psBook, null);
            // But manual closing is fine too:
            try { if (psReview != null) psReview.close(); } catch (Exception e) {}
            try { if (psBook != null) psBook.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return isDeleted;
    }
    // --- HELPER METHODS ---

    // Helper method to map results
    private BookInfo mapResultSetToBook(ResultSet rs) throws SQLException {
        return new BookInfo(
                rs.getInt("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("BOOK_AUTHOR"),
                rs.getString("BOOK_CATEGORY"),
                rs.getString("BOOK_DESCRIPTION"), // Correct mapping
                rs.getDouble("BOOK_PRICE"),
                rs.getInt("BOOK_QUANTITY"),
                rs.getString("BOOK_IMAGE_PATH")
        );
    }

    // Helper to close resources (You were missing this!)
    private void closeResources(ResultSet rs, Statement stmt, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }

    public List<BookInfo> getBooksForCatalogue() {
        List<BookInfo> books = new ArrayList<>();
        String sql = "SELECT BOOK_ID, BOOK_TITLE, BOOK_CATEGORY, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM BOOK_TB";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BookInfo book = new BookInfo(
                        rs.getInt("BOOK_ID"),
                        rs.getString("BOOK_TITLE"),
                        "", // author not needed for catalogue
                        rs.getString("BOOK_CATEGORY"),
                        "", // synopsis not needed for catalogue
                        rs.getDouble("BOOK_PRICE"),
                        rs.getInt("BOOK_QUANTITY"),
                        rs.getString("BOOK_IMAGE_PATH"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching books for catalogue: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    // Search books by title for catalogue display
    public List<BookInfo> searchBooksForCatalogue(String titleInput) {
        List<BookInfo> books = new ArrayList<>();
        String query = "SELECT BOOK_ID, BOOK_TITLE, BOOK_CATEGORY, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM BOOK_TB WHERE BOOK_TITLE LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + titleInput + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new BookInfo(
                            rs.getInt("BOOK_ID"),
                            rs.getString("BOOK_TITLE"),
                            "",
                            rs.getString("BOOK_CATEGORY"),
                            "",
                            rs.getDouble("BOOK_PRICE"),
                            rs.getInt("BOOK_QUANTITY"),
                            rs.getString("BOOK_IMAGE_PATH")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Filter books by category for catalogue display
    public List<BookInfo> filterBooksByCategoryForCatalogue(String category) {
        List<BookInfo> books = new ArrayList<>();
        String query = "SELECT BOOK_ID, BOOK_TITLE, BOOK_CATEGORY, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM BOOK_TB WHERE BOOK_CATEGORY = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new BookInfo(
                            rs.getInt("BOOK_ID"),
                            rs.getString("BOOK_TITLE"),
                            "",
                            rs.getString("BOOK_CATEGORY"),
                            "",
                            rs.getDouble("BOOK_PRICE"),
                            rs.getInt("BOOK_QUANTITY"),
                            rs.getString("BOOK_IMAGE_PATH")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
//testing
