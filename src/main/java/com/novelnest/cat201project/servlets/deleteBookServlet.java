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
//ORACLE
@WebServlet("/DeleteBookServlet")
public class deleteBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get the ID from the URL parameter (?id=...)
        String idStr = request.getParameter("id");

        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            BookDAO dao = new BookDAO();

            // 2. Execute deletion
            boolean success = dao.deleteBook(id);

            if (success) {
                System.out.println("Successfully deleted book ID: " + id);
            }
        }

        // 3. ALWAYS redirect back to the catalog
        response.sendRedirect(request.getContextPath() + "/resources/pages/Book/viewBook.jsp");    }
}