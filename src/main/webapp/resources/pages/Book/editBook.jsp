<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>

<%
    // Fetch the book details before rendering the form
    String idParam = request.getParameter("id");
    BookInfo book = null;
    if (idParam != null) {
        int id = Integer.parseInt(idParam);
        BookDAO dao = new BookDAO();
        book = dao.getBookById(id);
    }

    // Redirect if book is not found to prevent "Cannot resolve symbol" errors
    if (book == null) {
        response.sendRedirect("viewBooks.jsp?error=notfound");
        return;
    }
%>

<html>
<head>
    <title>NovelNest - Edit Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h2>Edit Book Details</h2>

    <form id="editBookForm" action="${pageContext.request.contextPath}/UpdateBookServlet" method="post">
        <input type="hidden" name="id" value="<%= book.getId() %>">

        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" name="title" id="title" value="<%= book.getTitle() %>" required/>
        </div>

        <div class="form-group">
            <label for="price">Price (RM):</label>
            <input type="number" step="0.01" name="price" id="price" value="<%= book.getPrice() %>" required/>
        </div>

        <div class="form-group">
            <label for="quantity">Stock Quantity:</label>
            <input type="number" name="quantity" id="quantity" value="<%= book.getQuantity() %>" required/>
        </div>

        <div class="form-group">
            <label for="synopsis">Synopsis:</label>
            <textarea name="synopsis" id="synopsis" required><%= book.getSynopsis() %></textarea>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-save">Save Changes</button>
            <a href="viewBook.jsp" class="btn-cancel">Cancel</a>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/bookFunction.js"></script>

</body>
</html>