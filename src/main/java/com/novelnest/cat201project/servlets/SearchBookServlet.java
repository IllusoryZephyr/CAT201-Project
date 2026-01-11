package com.novelnest.cat201project.servlets;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.novelnest.cat201project.dao.BookDAO;
import com.novelnest.cat201project.models.BookInfo;

@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Get search parameters from request
        String query = request.getParameter("query");
        String category = request.getParameter("category");

        BookDAO dao = new BookDAO();
        List<BookInfo> books;

        // Determine search type based on parameters provided
        if (query != null && !query.trim().isEmpty()) {
            books = dao.searchBooksForCatalogue(query.trim());
        } else if (category != null && !category.trim().isEmpty()) {
            books = dao.filterBooksByCategoryForCatalogue(category.trim());
        } else {
            books = dao.getBooksForCatalogue();
        }

        // Set the books list
        request.setAttribute("books", books);
        request.setAttribute("searchQuery", query);
        request.setAttribute("selectedCategory", category);

        // Forward to the catalogue JSP page to display results
        request.getRequestDispatcher("/resources/pages/Catalogue/catalogue.jsp").forward(request, response);
    }
}