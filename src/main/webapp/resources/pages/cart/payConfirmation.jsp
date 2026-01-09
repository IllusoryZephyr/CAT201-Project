<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Confirmed - NovelNest</title>

</head>
<body>

<div class="container">
    <div class="success-container">
        <h1 >Thank You for Your Order!</h1>
        <p>Your payment has been processed successfully.</p>

        <div class="order-id">
            Order ID: #<%= request.getParameter("orderId") %>
        </div>

        <p>A confirmation email has been sent to your registered address.</p>
        <br>

        <a href="home.jsp" class="btn btn-primary">Continue Shopping</a>
    </div>
</div>

</body>
</html>