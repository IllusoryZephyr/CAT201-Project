package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.UserDAO;
import com.novelnest.cat201project.models.UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            UserInfo user = new UserInfo();

            user.setName(request.getParameter("user_name"));
            user.setPassword(request.getParameter("user_password"));

            UserDAO userDAO = new UserDAO();

            if (userDAO.addUser(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", user.getId());
                session.setAttribute("user_name", user.getName());
                session.setAttribute("user_creation_date", user.getCreated());
                session.setAttribute("user_is_admin", user.isAdmin());

                //response.sendRedirect(request.getContextPath() + "/resources/pages/user/home.jsp");
                response.sendRedirect(request.getContextPath() + "/resources/pages/user/testServlet.jsp");
            }
            else {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/resources/pages/user/signup.jsp").forward(request, response);
            }
        }

        else if (action.equals("edit")) {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("user_id") != null) {
                UserInfo user = new UserInfo();

                user.setId((int) session.getAttribute("user_id"));
                user.setName(request.getParameter("user_name"));
                user.setPassword(request.getParameter("user_password"));

                UserDAO userDAO = new UserDAO();

                if (userDAO.editUser(user)) {
                    response.sendRedirect(request.getContextPath() + "/resources/pages/user/profile.jsp");
                }
                else {
                    request.setAttribute("error", "Failed to update user.");
                    request.getRequestDispatcher("/resources/pages/user/editUser.jsp").forward(request, response);
                }
            }
            else {
                request.setAttribute("error", "User not logged in.");
                request.getRequestDispatcher("/resources/pages/user/login.jsp").forward(request, response);
            }
        }

        else if (action.equals("delete")) {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("user_id") != null) {
                UserInfo user = new UserInfo();

                user.setId((int) session.getAttribute("user_id"));

                UserDAO userDAO = new UserDAO();

                if (userDAO.deleteUser(user)) {
                    session.invalidate();

                    response.sendRedirect(request.getContextPath() + "/resources/pages/user/signup.jsp");
                }
                else {
                    request.setAttribute("error", "Could not delete user.");
                    //request.getRequestDispatcher("/resources/pages/user/editUser.jsp").forward(request, response);
                    request.getRequestDispatcher("/resources/pages/user/testServlet.jsp").forward(request, response);
                }
            }
            else {
                request.setAttribute("error", "User not logged in.");
                request.getRequestDispatcher("/resources/pages/user/login.jsp").forward(request, response);
            }
        }

        else if (action.equals("authenticate")) {
            UserInfo user = new UserInfo();

            user.setName(request.getParameter("user_name"));
            user.setPassword(request.getParameter("user_password"));

            UserDAO userDAO = new UserDAO();

            if (userDAO.checkUserPassword(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", user.getId());
                session.setAttribute("user_name", user.getName());
                session.setAttribute("user_creation_date", user.getCreated());
                session.setAttribute("user_is_admin", user.isAdmin());

                //response.sendRedirect(request.getContextPath() + "/resources/pages/home.jsp");
                response.sendRedirect(request.getContextPath() + "/resources/pages/user/testServlet.jsp");
            }
            else {
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/resources/pages/user/login.jsp").forward(request, response);
            }
        }

        else if (action.equals("logout")) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + "/resources/pages/user/login.jsp");
        }
    }
}
