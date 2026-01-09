<%@ page import="java.util.List" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Book Catalog</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h2>Our Book Collection</h2>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Synopsis</th>
        <th>Price</th>
        <th>Stock</th>
    </tr>
    </thead>
    <tbody>
    <%
        try {
            BookDAO dao = new BookDAO();
            List<BookInfo> bookList = dao.getAllBooks();

            if(bookList != null && !bookList.isEmpty()) {
                for(BookInfo book : bookList) {
                    // Opening the loop inside Java tags
    %>
    <tr>
        <td><%= book.getId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getSynopsis() %></td>
        <td>RM <%= String.format("%.2f", book.getPrice()) %></td>
        <td><%= book.getQuantity() %> units</td>
    </tr>
    <%
        } // This closes the for loop
    } else {
    %>
    <tr><td colspan='3'>No books found in catalog.</td></tr>
    <%
        }
    } catch (Exception e) {
    %>
    <tr><td colspan='3' style='color:red;'>Error: <%= e.getMessage() %></td></tr>
    <%
            e.printStackTrace(); // This helps you see the real error in Tomcat logs
        }
    %>
    </tbody>
</table>

<script src="script.js"></script>
</body>
</html>