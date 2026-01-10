package com.novelnest.cat201project.dao;
import com.novelnest.cat201project.models.BookInfo;
import com.novelnest.cat201project.dao.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//sql

public class BookDAO {
    public boolean addBook(BookInfo book) {
        String sql = "INSERT INTO BOOKS (BOOK_TITLE, BOOK_AUTHOR, BOOK_CATEGORY, BOOK_SYNOPSIS, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH) VALUES (?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getCategory());
            pstmt.setString(4, book.getSynopsis()); // Oracle JDBC maps String to CLOB automatically
            pstmt.setDouble(5, book.getPrice());
            pstmt.setInt(6, book.getQuantity());
            pstmt.setString(7, book.getImagePath());

            return pstmt.executeUpdate() > 0;

            //boolean success = pstmt.executeUpdate() > 0;
            /*if (success && !conn.getAutoCommit()) {
                conn.commit();
            }
            return success;*/
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
        //String sql = "SELECT BOOK_ID, BOOK_TITLE, BOOK_AUTHOR, BOOK_CATEGORY, BOOK_SYNOPSIS, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM BOOKS";

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
                                rs.getInt("BOOK_ID"),           // Lowercase as per your list
                                rs.getString("BOOK_TITLE"),     // Uppercase as per your list
                                rs.getString("BOOK_AUTHOR"),
                                rs.getString("BOOK_CATEGORY"),
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

    public boolean updateBook(BookInfo book) {
        String sql = "UPDATE books SET book_title = ?, book_author = ?, book_category = ?, book_synopsis = ?, book_price = ?, book_quantity = ?, book_image_path = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getCategory());
            pstmt.setString(4, book.getSynopsis());
            pstmt.setDouble(5, book.getPrice());
            pstmt.setInt(6, book.getQuantity());
            pstmt.setString(7, book.getImagePath());
            pstmt.setInt(8, book.getId()); // The ID is used in the WHERE clause

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BookInfo getBookById(int id) {
        String sql = "SELECT * FROM books WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Mapping the database columns to your BookInfo model
                    return new BookInfo(
                            rs.getInt("book_id"),
                            rs.getString("book_title"),
                            rs.getString("book_author"),
                            rs.getString("book_category"),
                            rs.getString("book_synopsis"),
                            rs.getDouble("book_price"),
                            rs.getInt("book_quantity"),
                            rs.getString("book_image_path")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching book by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Returns null if the book isn't found
    }

    public List<BookInfo> getBooksForCatalogue() {
        List<BookInfo> books = new ArrayList<>();
        String sql = "SELECT BOOK_ID, BOOK_TITLE, BOOK_CATEGORY, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM BOOKS";

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
        String query = "SELECT BOOK_ID, BOOK_TITLE, BOOK_CATEGORY, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM books WHERE BOOK_TITLE LIKE ?";

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
        String query = "SELECT BOOK_ID, BOOK_TITLE, BOOK_CATEGORY, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH FROM books WHERE BOOK_CATEGORY = ?";

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



//ORACLE
/*
public class BookDAO {

    // 1. CREATE: Optimized for Oracle Identity/Trigger
    public boolean addBook(BookInfo book) {
        String sql = "INSERT INTO BOOKS (BOOK_TITLE, BOOK_SYNOPSIS, BOOK_PRICE, BOOK_QUANTITY, BOOK_IMAGE_PATH) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getSynopsis());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setString(5, book.getImagePath());

            int rowsAffected = pstmt.executeUpdate();

            // CRITICAL FOR ORACLE: Manual commit if AutoCommit is disabled
            if (rowsAffected > 0 && !conn.getAutoCommit()) {
                conn.commit();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Oracle Insert Error: " + e.getMessage());
            return false;
        }
    }

    // 2. READ: Uses UPPERCASE labels to match Oracle metadata
    public List<BookInfo> getAllBooks() {
        List<BookInfo> books = new ArrayList<>();
        String sql = "SELECT * FROM BOOKS ORDER BY BOOK_ID DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new BookInfo(
                        rs.getInt("BOOK_ID"),           // Oracle defaults to UPPERCASE
                        rs.getString("BOOK_TITLE"),
                        rs.getString("BOOK_SYNOPSIS"),
                        rs.getDouble("BOOK_PRICE"),
                        rs.getInt("BOOK_QUANTITY"),
                        rs.getString("BOOK_IMAGE_PATH")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Oracle Fetch Error: " + e.getMessage());
        }
        return books;
    }

    // 3. UPDATE: Fixed for Oracle case sensitivity
    public boolean updateBook(BookInfo book) {
        String sql = "UPDATE BOOKS SET BOOK_TITLE = ?, BOOK_SYNOPSIS = ?, BOOK_PRICE = ?, BOOK_QUANTITY = ?, BOOK_IMAGE_PATH = ? WHERE BOOK_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getSynopsis());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setString(5, book.getImagePath());
            pstmt.setInt(6, book.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0 && !conn.getAutoCommit()) {
                conn.commit();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Oracle Update Error: " + e.getMessage());
            return false;
        }
    }

    // 4. DELETE
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM BOOKS WHERE BOOK_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0 && !conn.getAutoCommit()) {
                conn.commit();
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Oracle Delete Error: " + e.getMessage());
            return false;
        }
    }

    // 5. GET SINGLE BOOK
    public BookInfo getBookById(int id) {
        String sql = "SELECT * FROM BOOKS WHERE BOOK_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new BookInfo(
                            rs.getInt("BOOK_ID"),
                            rs.getString("BOOK_TITLE"),
                            rs.getString("BOOK_SYNOPSIS"),
                            rs.getDouble("BOOK_PRICE"),
                            rs.getInt("BOOK_QUANTITY"),
                            rs.getString("BOOK_IMAGE_PATH")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Oracle ID Fetch Error: " + e.getMessage());
        }
        return null;
    }
}

 */