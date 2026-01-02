package com.novelnest.cat201project.servlets;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.novelnest.cat201project.dao.BookDAO;
import com.novelnest.cat201project.models.BookInfo;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get data from form
        String title = request.getParameter("title");
        String synopsis = request.getParameter("synopsis");

        // Use a try-catch or check for null if price/quantity could be empty
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imagePath = request.getParameter("imagePath");

        // 2. Create Object (Matches your updated constructor)
        BookInfo newBook = new BookInfo(0, title, synopsis, price, quantity, imagePath);

        // 3. Save to DB
        BookDAO dao = new BookDAO();
        if (dao.addBook(newBook)) {
            // Add the leading "/" and the full folder path
            response.sendRedirect(request.getContextPath() + "/resources/pages/Book/viewBook.jsp");
        }
        else {
            response.getWriter().println("Error adding book to database!");
        }
    }
}