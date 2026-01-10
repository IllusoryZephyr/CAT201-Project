package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.BookInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 1. CREATE: Returns Generated ID (int) instead of boolean
    public int addBook(BookInfo book) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = -1;

        // Oracle INSERT - Do not insert BOOK_ID if you have a Trigger/Identity column
        String sql = "INSERT INTO HR.BOOKS (BOOK_TITLE, BOOK_SYNOPSIS, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH) VALUES (?, ?, ?, ?, ?)";

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false); // Manual commit for Oracle safety

            // Request the generated key (BOOK_ID)
            ps = con.prepareStatement(sql, new String[]{"BOOK_ID"});

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getSynopsis());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getQuantity());
            ps.setString(5, book.getImagePath());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated ID
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
                con.commit(); // SAVE CHANGES
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return generatedId;
    }

    // 2. READ ALL
    public List<BookInfo> getAllBooks() {
        List<BookInfo> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM HR.BOOKS ORDER BY BOOK_ID";

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

    public BookInfo getBookById(int id) {
        BookInfo book = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM HR.BOOKS WHERE BOOK_ID = ?";

        try {
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                book = new BookInfo(
                        rs.getInt("BOOK_ID"),           // Oracle is case sensitive (usually Upper)
                        rs.getString("BOOK_TITLE"),
                        rs.getString("BOOK_SYNOPSIS"),
                        rs.getDouble("BOOK_PRICE"),
                        rs.getInt("BOOK_QUANTITY"),
                        rs.getString("BOOK_IMAGE_PATH")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return book;
    }

    // 4. UPDATE
    public boolean updateBook(BookInfo book) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        String sql = "UPDATE HR.BOOKS SET BOOK_TITLE = ?, BOOK_SYNOPSIS = ?, BOOK_PRICE = ?, BOOK_QUANTITY = ?, BOOK_IMAGE_PATH = ? WHERE BOOK_ID = ?";

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getSynopsis());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getQuantity());
            ps.setString(5, book.getImagePath());
            ps.setInt(6, book.getId());

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

    // 5. DELETE
    public boolean deleteBook(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        String sql = "DELETE FROM HR.BOOKS WHERE BOOK_ID = ?";

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

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

    // Helper method to map results (Handles Oracle Uppercase)
    private BookInfo mapResultSetToBook(ResultSet rs) throws SQLException {
        return new BookInfo(
                rs.getInt("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("BOOK_SYNOPSIS"),
                rs.getDouble("BOOK_PRICE"),
                rs.getInt("BOOK_QUANTITY"),
                rs.getString("BOOK_IMAGE_PATH")
        );
    }

    // Helper to close resources
    private void closeResources(ResultSet rs, Statement stmt, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}