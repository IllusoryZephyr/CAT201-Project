<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Add New Book</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h2>Add a New Book to Catalog</h2>
<form action="${pageContext.request.contextPath}/AddBookServlet" method="post">    <input type="text" name="title" placeholder="Book Title" required><br>
    <textarea name="synopsis" placeholder="Synopsis"></textarea><br>
    <input type="number" step="0.01" name="price" placeholder="Price (RM)" required><br>
    <input type="number" name="quantity" placeholder="Quantity" required><br>
    <input type="text" name="imagePath" placeholder="Image Path (e.g., images/book.jpg)"><br>
    <button type="submit">Add Book</button>
</form>
</body>
</html>