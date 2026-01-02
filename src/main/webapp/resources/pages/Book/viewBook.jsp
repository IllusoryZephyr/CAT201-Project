<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Book Catalog</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="container">
    <h2>Our Book Collection</h2>

    <div style="margin-bottom: 20px;">
        <a href="addBook.jsp" class="btn-add">+ Add New Book</a>
    </div>

    <%-- ... existing imports ... --%>
    <table border="1" class="book-table">
        <thead>
        <tr>
            <th>ID</th><th>Title</th><th>Synopsis</th>
            <th>Price</th><th>Stock</th><th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            try {
                BookDAO dao = new BookDAO();
                List<BookInfo> bookList = dao.getAllBooks();
                if(bookList != null && !bookList.isEmpty()) {
                    for(BookInfo book : bookList) {
        %>
        <tr>
            <td><%= book.getId() %></td>
            <td><strong><%= book.getTitle() %></strong></td>
            <td><%= book.getSynopsis() %></td>
            <td>RM <%= String.format("%.2f", book.getPrice()) %></td>
            <td><%= book.getQuantity() %> units</td>
            <td>
                <a href="${pageContext.request.contextPath}/DeleteBookServlet?id=<%= book.getId() %>"
                   class="btn-delete"
                   data-title="<%= book.getTitle() %>">Delete</a>
            </td>
        </tr>
        <% } } else { %>
        <tr><td colspan="6">No books found in catalog.</td></tr>
        <% } } catch (Exception e) { %>
        <tr><td colspan="6" style="color:red;">Error: <%= e.getMessage() %></td></tr>
        <% } %>
        </tbody>
    </table>

    <script src="${pageContext.request.contextPath}/bookFunction.js"></script>

</body>
</html>