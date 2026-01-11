<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Order Confirmed - NovelNest</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/cart/payConfirmation.css">
</head>

<body>

<div class="container">
    <div class="success-container">
        <h1>Thank You for Your Order!</h1>
        <p>Your payment has been processed successfully.</p>

        <div class="order-id">
            Order ID: #<%= request.getParameter("orderId") %>
        </div>

        <br>

        <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/catalogue.jsp"
           class="btn btn-primary">Continue Shopping</a>
    </div>
</div>

</body>

</html>