<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Add New Book</title>
    <link rel="stylesheet" type="text/css" href="addBook.css">
    <script>const contextPath = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/resources/pages/Book/bookFunction.js"></script>
</head>
<body>
<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="page-wrapper">
    <div class="container">
        <h2>Add a New Book to Catalog</h2>

        <form action="${pageContext.request.contextPath}/AddBookServlet" method="post">
            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Title:</label>
            <input type="text" name="title" placeholder="Book Title" required>

            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Author:</label>
            <input type="text" name="author" placeholder="Author Name" required>

            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Category:</label>
            <input type="text" name="category" placeholder="Category (e.g., Fiction)" required>

            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Synopsis:</label>
            <textarea name="synopsis" placeholder="Synopsis" rows="4"></textarea>

            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Price (RM):</label>
            <input type="number" step="0.01" name="price" placeholder="0.00" required>

            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Stock Quantity:</label>
            <input type="number" name="quantity" placeholder="0" required>

            <label style="font-weight: 600; color: #5d6d7e; font-size: 14px;">Image Path:</label>
            <input type="text" name="imagePath" placeholder="images/book.jpg">

            <button type="submit">Add Book</button>
            <a href="viewBook.jsp" class="btn-cancel">Cancel</a>
        </form>
    </div>
</div>

<jsp:include page="../../common/footer/footer.jsp" />
</body>
</html>