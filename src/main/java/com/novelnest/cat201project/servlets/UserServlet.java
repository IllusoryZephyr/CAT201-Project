package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.UserDAO;
import com.novelnest.cat201project.models.UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            UserInfo user = new UserInfo();

            user.setName(request.getParameter("name"));
            user.setPassword(request.getParameter("password"));

            UserDAO userDAO = new UserDAO();

            if (userDAO.addUser(user)) {
                response.sendRedirect("/resources/pages/user/login.jsp");
            }
            else {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/resources/pages/user/signup.jsp").forward(request, response);
            }
        }

        /*else if (action.equals("delete")) {
        }*/
    }
}
