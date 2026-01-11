package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.CustomerServiceDAO;
import com.novelnest.cat201project.models.MessageInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerServiceServlet")
public class CustomerServiceServlet extends HttpServlet {
    private final CustomerServiceDAO dao = new CustomerServiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (session.getAttribute("user_id") == null) {
            response.sendRedirect(request.getContextPath() + "/resources/pages/user/login.jsp");
            return;
        }

        if (action.equals("viewChat")) {
            String ownerIdParam = request.getParameter("ownerId");

            if (ownerIdParam != null) {
                session.setAttribute("chat_owner_id", Integer.parseInt(ownerIdParam));
            }

            Integer ownerId = (Integer) session.getAttribute("chat_owner_id");
            List<MessageInfo> chatHistory = dao.getMessagesByOwnerID(ownerId);

            request.setAttribute("chatHistory", chatHistory);
            request.getRequestDispatcher("/resources/pages/user/customerService.jsp").forward(request, response);
        }
        else if (action.equals("viewAllChats")) {
            List<List<MessageInfo>> allChats = dao.getAllMessages();
            request.setAttribute("allChats", allChats);

            request.getRequestDispatcher("/resources/pages/user/allMessages.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer ownerId = (Integer) session.getAttribute("chat_owner_id");
        Integer userId = (Integer) session.getAttribute("user_id");
        String messageText = request.getParameter("message");

        if (userId != null && messageText != null && !messageText.trim().isEmpty()) {
            MessageInfo msg = new MessageInfo(ownerId, userId, messageText);

            dao.addMessage(msg);
        }

        response.sendRedirect(request.getContextPath() + "/CustomerServiceServlet?action=viewChat");
    }
}