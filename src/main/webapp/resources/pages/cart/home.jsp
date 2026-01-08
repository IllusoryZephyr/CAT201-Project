
<%-- ----------------------------- FAKE home.jsp for testing -------------------------------- --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest Bookstore</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container" style="text-align: center; margin-top: 50px;">
    <h1>Welcome to NovelNest</h1>
    <p>Test your cart by adding this sample book:</p>

    <div style="border: 1px solid #ddd; padding: 20px; display: inline-block; width: 250px;">
        <img src="https://via.placeholder.com/150" alt="Java Book" style="width:100px;">
        <h3>Java Programming</h3>
        <p>RM 50.00</p>

        <form action="${pageContext.request.contextPath}/CartServlet" method="post">
            <input type="hidden" name="action" value="add">

            <input type="hidden" name="bookId" value="101">
            <input type="hidden" name="title" value="Java Programming">
            <input type="hidden" name="price" value="50.00">
            <input type="hidden" name="image" value="https://via.placeholder.com/150">

            <button type="submit" style="padding: 10px 20px; background: #28a745; color: white; border: none; cursor: pointer;">
                Add to Cart
            </button>
        </form>
    </div>

    <br><br>
    <a href="Cart.jsp">View Cart</a>
</div>
</body>
</html>