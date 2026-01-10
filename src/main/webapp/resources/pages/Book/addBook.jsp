<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Add New Book</title>
    <link rel="stylesheet" type="text/css" href="addBook.css">
    <script>const contextPath = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/resources/js/bookFunction.js"></script>
</head>
<body>
<div class="container">
    <h2>Add a New Book to Catalog</h2>

    <form action="${pageContext.request.contextPath}/AddBookServlet" method="post">

        <input type="text" name="title" placeholder="Book Title" required><br>

        <input type="text" name="author" placeholder="Author Name" required><br>

        <input type="text" name="category" placeholder="Category (e.g., Fiction)" required><br>

        <textarea name="synopsis" placeholder="Synopsis" rows="4"></textarea><br>

        <input type="number" step="0.01" name="price" placeholder="Price (RM)" required><br>

        <input type="number" name="quantity" placeholder="Quantity" required><br>

        <input type="text" name="imagePath" placeholder="Image Path (e.g., images/book.jpg)"><br>

        <button type="submit">Add Book</button>
        <a href="viewBook.jsp" class="btn-cancel">Cancel</a>
    </form>
</div>
</body>
</html>