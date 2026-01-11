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

        request.setCharacterEncoding("UTF-8");

        try {
            // Get data
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String category = request.getParameter("category");
            String synopsis = request.getParameter("synopsis");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String imagePath = request.getParameter("imagePath");

            BookInfo book = new BookInfo(id, title, author, category, synopsis, price, quantity, imagePath);

            BookDAO dao = new BookDAO();
            boolean success = dao.updateBook(book);

            // Redirect
            if (success) {
                response.sendRedirect(request.getContextPath() + "/resources/pages/Book/viewBook.jsp");
            } else {
                response.sendRedirect("editBook.jsp?id=" + id + "&error=failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("editBook.jsp?error=invalid_input");
        }
    }
}