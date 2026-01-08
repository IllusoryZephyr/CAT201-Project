<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.novelnest.cat201project.models.Cart" %>
<%@ page import="com.novelnest.cat201project.models.CartItem" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Shopping Cart - NovelNest</title>
    <link rel="stylesheet" href="">
    <script src=""></script>
</head>
<body>

<div class="container">
    <h2>Your Shopping Cart</h2>

    <%
        // retrieve cart from session
        Cart cart = (Cart) session.getAttribute("cart");

        // check cart is empty or is null
        if (cart == null || cart.getItems().isEmpty()) {
    %>
    <div class="empty-cart">
        <p>Your cart is currently empty.</p>
        <a href="home.jsp" class="btn btn-primary">Browse Books</a> <%-- home.jsp could be other page like book --%>
    </div>
    <%
    } else {
    %>
    <table class="cart-table">
        <thead>
        <tr>
            <th>Book</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Loop through each item in the cart
            for (CartItem item : cart.getItems()) {
                BookInfo book = item.getBook();
        %>
        <tr>
            <td>
                <div class="cart-book-info">
                    <img src="<%= book.getImagePath() %>" alt="Cover" class="book-thumb">
                    <div>
                        <strong><%= book.getTitle() %></strong>
                        <br>
                        <small>ID: <%= book.getId() %></small>
                    </div>
                </div>
            </td>
            <td>RM <%= String.format("%.2f", book.getPrice()) %></td>

            <td>
                <form action="${pageContext.request.contextPath}/CartServlet" method="post" class="qty-form">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" style="width: 50px;">
                    <button type="submit" class="btn-small">Update</button>
                </form>
            </td>

            <td>RM <%= String.format("%.2f", item.getTotalPrice()) %></td>

            <td>
                <form action="${pageContext.request.contextPath}/CartServlet" method="post" onsubmit="return confirmRemove()">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button type="submit" class="btn-remove">Remove</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="cart-summary">
        <h3>Grand Total: RM <%= String.format("%.2f", cart.grandTotal()) %></h3>

        <div class="cart-actions">
            <a href="home.jsp" class="btn btn-secondary">Continue Shopping</a>
            <a href="checkout.jsp" class="btn btn-success">Proceed to Checkout</a>
        </div>
    </div>
    <%
        }
    %>
</div>

</body>
</html>