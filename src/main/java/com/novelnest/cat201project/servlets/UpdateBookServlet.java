package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.BookDAO;
import com.novelnest.cat201project.models.BookInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // IMPORTANT: Set encoding before reading any parameters for Oracle compatibility
        request.setCharacterEncoding("UTF-8");

        try {
            // 1. Get data from the form
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String synopsis = request.getParameter("synopsis");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String imagePath = request.getParameter("imagePath");

            // 2. Create a BookInfo object
            BookInfo book = new BookInfo(id, title, synopsis, price, quantity, imagePath);

            // 3. Call DAO to update
            BookDAO dao = new BookDAO();
            boolean success = dao.updateBook(book);

            // 4. Redirect
            if (success) {
                // sendRedirect is best for Oracle to ensure the COMMIT is fully visible
                response.sendRedirect(request.getContextPath() + "/resources/pages/Book/viewBook.jsp");
            } else {
                response.sendRedirect("editBook.jsp?id=" + id + "&error=failed");
            }
        } catch (NumberFormatException e) {
            // Handle cases where ID, Price, or Quantity are not valid numbers
            response.sendRedirect("editBook.jsp?error=invalid_input");
        }
    }
}