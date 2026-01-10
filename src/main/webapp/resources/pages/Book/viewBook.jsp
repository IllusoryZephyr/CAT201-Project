<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Book Catalog</title>
    <link rel="stylesheet" type="text/css" href="viewBook.css">
</head>
<body>

<div class="container">
    <%
        // 1. Logic for Single Book View (What your JS needs)
        String idParam = request.getParameter("id");
        BookInfo singleBook = null;
        BookDAO dao = new BookDAO();

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int bookId = Integer.parseInt(idParam);
                singleBook = dao.getBookById(bookId);
            } catch (Exception e) { /* handle error */ }
        }
    %>

    <%-- DISPLAY SINGLE BOOK DETAILS (CRITICAL FOR bookFunction.js) --%>
    <% if (singleBook != null) { %>
    <div class="book-details" style="background: #f9f9f9; padding: 20px; border-radius: 8px; margin-bottom: 30px;">
        <h1><%= singleBook.getTitle() %></h1>
        <p><strong>Synopsis:</strong> <%= singleBook.getSynopsis() %></p>
        <p><strong>Price:</strong> RM <%= String.format("%.2f", singleBook.getPrice()) %></p>

        <%-- This hidden input fixes the "Invalid ID" console error --%>
        <input type="hidden" id="bookId" value="<%= singleBook.getId() %>">
    </div>
    <% } %>

    <h2>Full Book Collection</h2>
    <div style="margin-bottom: 20px;">
        <a href="addBook.jsp" class="btn-add">+ Add New Book</a>
    </div>

    <table border="1" class="book-table">
        <thead>
        <tr>
            <th>ID</th><th>Title</th><th>Price</th><th>Stock</th><th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            try {
                List<BookInfo> bookList = dao.getAllBooks();
                if(bookList != null && !bookList.isEmpty()) {
                    for(BookInfo book : bookList) {
        %>
        <tr>
            <td><%= book.getId() %></td>
            <td><strong><%= book.getTitle() %></strong></td>
            <td>RM <%= String.format("%.2f", book.getPrice()) %></td>
            <td><%= book.getQuantity() %> units</td>
            <td>
                <%-- Link to load THIS specific book details above --%>
                <a href="viewBook.jsp?id=<%= book.getId() %>" class="btn-edit">View</a>
                <a href="editBook.jsp?id=<%= book.getId() %>" class="btn-edit">Edit</a>
                <a href="${pageContext.request.contextPath}/DeleteBookServlet?id=<%= book.getId() %>" class="btn-delete">Delete</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr><td colspan="5">No books found in Oracle database.</td></tr>
        <%      }
        } catch (Exception e) {
        %>
        <tr><td colspan="5" style="color:red;">Error: <%= e.getMessage() %></td></tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="${pageContext.request.contextPath}/bookFunction.js"></script>
</body>
</html>