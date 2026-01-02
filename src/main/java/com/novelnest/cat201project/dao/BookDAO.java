package com.novelnest.cat201project.dao;
import com.novelnest.cat201project.models.BookInfo;
import com.novelnest.cat201project.dao.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public boolean addBook(BookInfo book) {
        String sql = "INSERT INTO books (book_title, book_synopsis, book_price, book_quantity, book_image_path) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getSynopsis());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setString(5, book.getImagePath());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // This will print the EXACT reason (e.g., "Table doesn't exist" or "Data too long")
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int id) {
        String sql = "DELETE FROM BOOKS WHERE BOOK_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            // pstmt.executeUpdate() returns an INT (number of rows deleted)
            int rowsDeleted = pstmt.executeUpdate();

            // We return TRUE if 1 or more rows were deleted
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BookInfo> getAllBooks() {
        List<BookInfo> books = new ArrayList<>();
        System.out.println("--- DEBUG START ---");

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("1. Connection Successful!");

                String sql = "SELECT * FROM BOOKS";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    System.out.println("2. Query Executed.");

                    if (!rs.isBeforeFirst()) {
                        System.out.println("3. WARNING: Database found, but table is EMPTY.");
                    }

                    while (rs.next()) {
                        BookInfo book = new BookInfo(
                                rs.getInt("BOOK_ID"),
                                rs.getString("BOOK_TITLE"),
                                rs.getString("BOOK_SYNOPSIS"),
                                rs.getDouble("BOOK_PRICE"),
                                rs.getInt("BOOK_QUANTITY"),
                                rs.getString("BOOK_IMAGE_PATH")
                        );
                        books.add(book);
                        System.out.println("4. Found Book: " + book.getTitle());
                    }
                }
            } else {
                System.out.println("ERROR: Connection object is NULL.");
            }
        } catch (Exception e) {
            System.out.println("FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("--- DEBUG END: Total Books = " + books.size() + " ---");
        return books;
    }
}