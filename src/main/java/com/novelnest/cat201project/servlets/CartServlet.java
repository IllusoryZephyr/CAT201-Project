package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.models.BookInfo;
import com.novelnest.cat201project.models.Cart;
import com.novelnest.cat201project.models.CartItem;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get Session and Cart
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // If cart doesn't exist yet, create it
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Determine the Action (add, remove, or update)
        String action = request.getParameter("action");

        // If action ==null
        if (action == null) {
            response.sendRedirect(request.getContextPath() +"/home.jsp");
            return;
        }


        // Get the Book ID
        int bookId = 0;
        try {
            bookId = Integer.parseInt(request.getParameter("bookId"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() +"/home.jsp"); // Invalid ID, go home or other page
            return;
        }

        // ACTION: ADD TO CART
        if ("add".equals(action)) {
            // Retrieve book details from the form
            String title = request.getParameter("title");
            String image = request.getParameter("image");
            double price = Double.parseDouble(request.getParameter("price"));

            // Create a temporary BookInfo object
            BookInfo book = new BookInfo(bookId, title, "Synopsis...", price, 1, image);

            // Add 1 to cart
            cart.addItem(book, 1);

            // Redirect to Cart Page to show the user what they added
            response.sendRedirect(request.getContextPath() + "/resources/pages/cart/Cart.jsp");
        }

        // ACTION: REMOVE ITEM
        else if ("remove".equals(action)) {
            cart.removeItem(bookId);
            response.sendRedirect(request.getContextPath() + "/resources/pages/cart/Cart.jsp");
        }

        // ACTION: UPDATE QUANTITY
        else if ("update".equals(action)) {
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Validate: Quantity must be at least 1
            if (quantity > 0) {
                for (CartItem item : cart.getItems()) {
                    if (item.getBook().getId() == bookId) {
                        item.setQuantity(quantity); //set new quantity
                        break; //stop looping after correct item is found
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/resources/pages/cart/Cart.jsp");
        }

        // Default fallback
        else {
            response.sendRedirect(request.getContextPath() +"/home.jsp");
        }
    }
}