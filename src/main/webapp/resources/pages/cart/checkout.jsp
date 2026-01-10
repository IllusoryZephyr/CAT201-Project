<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.novelnest.cat201project.models.Cart" %>
<%@ page import="com.novelnest.cat201project.models.CartItem" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>

<html>
<head>
    <title>Checkout - NovelNest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="checkout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/footer/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/navbar/navbar.css">
    <script src="checkoutValidation.js"></script>

    <script>

    </script>
</head>
<body>
<jsp:include page="/resources/common/navbar/navbar.jsp" />
<div class="container">
    <h2>Checkout</h2>

    <%
        // ensure cart is not empty
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            // If empty, go back to Cart page
            response.sendRedirect("Cart.jsp");
            return;
        }
    %>

    <div class="checkout-layout" >

        <div class="summary" >
            <h3>Order Summary</h3>
            <table border="1" >
                <% for (CartItem item : cart.getItems()) { %>
                <tr>
                    <td >
                        <strong><%= item.getBook().getTitle() %></strong> x <%= item.getQuantity() %>
                    </td>
                    <td style="padding: 10px;">
                        RM <%= String.format("%.2f", item.getTotalPrice()) %>
                    </td>
                </tr>
                <% } %>
            </table>
            <h3 >Total: RM <%= String.format("%.2f", cart.grandTotal()) %></h3>
        </div>

        <div class="form-section" >
            <h3>Shipping Details</h3>

            <form name="checkoutForm" action="${pageContext.request.contextPath}/CheckoutServlet" method="post" onsubmit="return validateCheckout()">

                <label>Shipping Address:</label><br>
                <textarea name="address" rows="4"  placeholder="123 Jalan University..."></textarea>
                <br><br>

                <label>Payment Method:</label><br>
                <select name="paymentMethod" style="width: 100%; padding: 5px;">
                    <option value="Credit Card">Credit / Debit Card</option>
                    <option value="Online Banking">Online Banking (FPX)</option>
                    <option value="E-Wallet">Touch 'n Go</option>
                    <option value="COD">Cash on Delivery</option>
                </select>
                <br><br>

                <div style="display: flex; justify-content: space-between;">
                    <a href="Cart.jsp" class="btn">Back to Cart</a>
                    <button type="submit" >Place Order</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/resources/common/footer/footer.jsp" />

</body>
</html>