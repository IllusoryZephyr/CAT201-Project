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
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            UserInfo user = new UserInfo();

            user.setName(request.getParameter("user_name"));
            user.setPassword(request.getParameter("user_password"));

            if (userDAO.addUser(user)) {
                HttpSession session = request.getSession();
                setSessionUser(session, user);

                response.sendRedirect(request.getContextPath() + "/resources/pages/user/profile.jsp");
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
                String user_name = request.getParameter("user_name");
                String user_password = request.getParameter("user_password");

                user.setId((int) session.getAttribute("user_id"));
                user.setCreated((LocalDateTime) session.getAttribute("user_creation_date"));
                user.setAdmin((Boolean) session.getAttribute("user_is_admin"));

                if (user_name != null){
                    user.setName(user_name);
                }
                else {
                    user.setName((String) session.getAttribute("user_name"));
                }

                if (user_password != null){
                    user.setPassword(user_password);
                }
                else {
                    user.setPassword((String) session.getAttribute("user_password"));
                }

                if (userDAO.editUser(user)) {
                    setSessionUser(session, user);
                    response.sendRedirect(request.getContextPath() + "/resources/pages/user/profile.jsp");
                }
                else {
                    if (user_name != null){
                        request.setAttribute("error", "Username already exists.");
                        request.getRequestDispatcher("/resources/pages/user/editUserName.jsp").forward(request, response);
                    }
                    else {
                        request.setAttribute("error", "Failed to update user.");
                        request.getRequestDispatcher("/resources/pages/user/editUserPassword.jsp").forward(request, response);
                    }
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

                if (userDAO.deleteUser(user)) {
                    session.invalidate();

                    response.sendRedirect(request.getContextPath() + "/resources/pages/user/signup.jsp");
                }
                else {
                    request.setAttribute("error", "Could not delete user.");
                    request.getRequestDispatcher("/resources/pages/user/editUser.jsp").forward(request, response);
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

            if (userDAO.checkUserPassword(user)) {
                HttpSession session = request.getSession();
                setSessionUser(session, user);

                response.sendRedirect(request.getContextPath() + "/resources/pages/home/Main.jsp");
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

        else if (action.equals("adminEditUser")) {
            UserInfo user = new UserInfo();

            user.setId(Integer.parseInt(request.getParameter("user_id")));
            user.setName(request.getParameter("user_name"));
            user.setPassword(request.getParameter("user_password"));
            user.setAdmin(Boolean.parseBoolean(request.getParameter("is_admin")));

            if (userDAO.editUser(user)) {
                response.sendRedirect(request.getContextPath() + "/UserServlet?action=manageUsers");
            } else {
                request.setAttribute("error", "Failed to update user.");
                request.getRequestDispatcher("/resources/pages/user/adminEditUser.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("manageUsers")) {
            List<UserInfo> allUsers = userDAO.getAllUser();

            request.setAttribute("userList", allUsers);
            request.getRequestDispatcher("/resources/pages/user/manageUsers.jsp").forward(request, response);
        }

        else if (action.equals("adminEditUserForm")){
            int userId = Integer.parseInt(request.getParameter("user_id"));

            UserInfo user = userDAO.getUserById(userId);

            request.setAttribute("userToEdit", user);

            request.getRequestDispatcher("/resources/pages/user/adminEditUser.jsp").forward(request, response);
        }
    }

    private void setSessionUser(HttpSession session, UserInfo user) {
        session.setAttribute("user_id", user.getId());
        session.setAttribute("user_name", user.getName());
        session.setAttribute("user_password", user.getPassword());
        session.setAttribute("user_creation_date", user.getCreated());
        session.setAttribute("user_is_admin", user.isAdmin());
    }
}
