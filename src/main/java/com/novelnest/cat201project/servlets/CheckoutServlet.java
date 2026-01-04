package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.OrderDao;
import com.novelnest.cat201project.dao.PaymentDao;
import com.novelnest.cat201project.models.Cart;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get Session and Cart
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Check is user logged in?
        Integer userId = (Integer) session.getAttribute("userId");

        // temporary user id before user module

        if (userId == null) {
            userId = 1;
            System.out.println("WARNING: Using Dummy User ID 1 for testing.");
        }

        //  Validation: Is Cart empty?
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp?error=EmptyCart");
            return;
        }

        //  Get Form Data
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");

        if (address == null || address.trim().isEmpty()) {
            response.sendRedirect("checkout.jsp?error=MissingAddress");
            return;
        }

        //  Save Order
        OrderDao orderDao = new OrderDao();
        int orderId = orderDao.saveOrder(cart, userId, address);

        if (orderId > 0) {
            // If Order success, Save Payment
            PaymentDao paymentDao = new PaymentDao();
            boolean paymentSuccess = paymentDao.savePayment(orderId, paymentMethod);

            if (paymentSuccess) {
                //  SUCCESS: Clear Cart and Redirect
                session.removeAttribute("cart");
                response.sendRedirect("confirmation.jsp?orderId=" + orderId);
            } else {
                // Payment failed
                response.sendRedirect("checkout.jsp?error=PaymentFailed");
            }
        } else {
            // Database Error
            response.sendRedirect("checkout.jsp?error=OrderFailed");
        }
    }
}