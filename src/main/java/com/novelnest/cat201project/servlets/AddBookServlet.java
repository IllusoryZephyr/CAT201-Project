package com.novelnest.cat201project.servlets;

import java.io.IOException;
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
            // 1. Get data from form
            String title = request.getParameter("title");
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

            // 2. Create Object (ID 0 is fine for Oracle Identity)
            BookInfo newBook = new BookInfo(0, title, synopsis, price, quantity, imagePath);

            // 3. Save to DB
// 3. Save to DB
            BookDAO dao = new BookDAO();
            int newBookId = dao.addBook(newBook); // Now returns the new ID

            if (newBookId != -1) {
                // SUCCESS: Pass the ID to the JSP
                response.sendRedirect(request.getContextPath() + "/resources/pages/Book/viewBook.jsp?id=" + newBookId);
            } else {
                response.getWriter().println("Error adding book to Oracle database!");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input: Price and Quantity must be numbers.");
        }
    }
}