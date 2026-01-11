package com.novelnest.cat201project.servlets;

import java.io.IOException;
import java.util.List;

import com.novelnest.cat201project.models.UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.novelnest.cat201project.dao.BookDAO;
import com.novelnest.cat201project.models.BookInfo;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Get data from form
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String category = request.getParameter("category");
            String synopsis = request.getParameter("synopsis");

            // Validate inputs aren't null or empty before parsing
            String priceStr = request.getParameter("price");
            String quantityStr = request.getParameter("quantity");

            if (priceStr == null || quantityStr == null) {
                response.getWriter().println("Error: Missing price or quantity");
                return;
            }

            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            String imagePath = request.getParameter("imagePath");

            // Create Object
            BookInfo newBook = new BookInfo(0, title, author, category, synopsis, price, quantity, imagePath);

            BookDAO dao = new BookDAO();
            int newBookId = dao.addBook(newBook); // Now returns the new ID

            if (newBookId != -1) {
                // success condition
                response.sendRedirect(request.getContextPath() + "/resources/pages/Book/viewBook.jsp?id=" + newBookId);
            } else {
                response.getWriter().println("Error adding book to Oracle database!");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input: Price and Quantity must be numbers.");
        }
    }

    //doGet method for admin to manage the book
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("viewAllBooks".equals(action)) {
            BookDAO bookDao = new BookDAO();
            List<BookInfo> books = bookDao.getAllBooks();
            request.setAttribute("bookList", books);
            request.getRequestDispatcher("/resources/pages/Book/viewBook.jsp").forward(request, response);        }
    }
}