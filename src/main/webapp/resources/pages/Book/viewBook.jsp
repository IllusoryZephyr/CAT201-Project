<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NovelNest - Book Catalog</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/pages/Book/viewBook.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="container">
    <div class="back-nav">
        <a href="${pageContext.request.contextPath}/resources/pages/user/adminDashboard.jsp" class="btn-back">
            <span class="arrow">&larr;</span> Back to Dashboard
        </a>
    </div>

    <%
        // 1. Logic for Single Book View
        String idParam = request.getParameter("id");
        BookInfo singleBook = null;
        BookDAO dao = new BookDAO();

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int bookId = Integer.parseInt(idParam);
                singleBook = dao.getBookById(bookId);
            } catch (Exception e) {
                /* Ignore parse errors */
            }
        }
    %>

    <%-- 2. DISPLAY SINGLE BOOK DETAILS --%>
    <% if (singleBook != null) { %>

    <div class="book-details" style="background: #f9f9f9; padding: 20px; border-radius: 8px; margin-bottom: 30px;">

        <div style="display: flex; gap: 30px; align-items: flex-start;">

            <div class="book-image-container">
                <%
                    String imgPath = singleBook.getImagePath();
                    String defaultImage = "images/default_book.png";

                    if (imgPath == null || imgPath.trim().isEmpty()) {
                        imgPath = defaultImage;
                    }
                %> <img src="${pageContext.request.contextPath}/<%= imgPath %>"
                        alt="<%= singleBook.getTitle() %>"
                        style="width: 200px; height: 300px; object-fit: cover; border-radius: 5px; box-shadow: 0 4px 8px rgba(0,0,0,0.2);"
                        onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/<%= defaultImage %>';">
            </div>

            <div class="book-info-container" style="flex: 1;">
                <h1 style="margin-top: 0;"><%= singleBook.getTitle() %></h1>

                <p><strong>Synopsis:</strong><br> <%= singleBook.getSynopsis() %></p>

                <h3 style="color: #27ae60;">Price: RM <%= String.format("%.2f", singleBook.getPrice()) %></h3>

                <p><strong>Stock:</strong> <%= singleBook.getQuantity() %> units available</p>

                <hr style="border: 0; border-top: 1px solid #ddd; margin: 15px 0;">

                <p class="book-author"><strong>Author:</strong> <%= singleBook.getAuthor() %></p>
                <p class="book-category"><strong>Category:</strong> <%= singleBook.getCategory() %></p>
            </div>

        </div> <%-- Hidden input for JavaScript --%>
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
            <th>ID</th>
            <th>Title</th>
            <th>Author</th> <th>Category</th> <th>Price</th>
            <th>Stock</th>
            <th>Action</th>
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

            <td><%= (book.getAuthor() != null) ? book.getAuthor() : "-" %></td>
            <td><%= (book.getCategory() != null) ? book.getCategory() : "-" %></td>

            <td>RM <%= String.format("%.2f", book.getPrice()) %></td>
            <td><%= book.getQuantity() %> units</td>
            <td>
                <a href="${pageContext.request.contextPath}/resources/pages/Book/viewBook.jsp?id=<%= book.getId() %>" class="btn-edit">View</a>
                <a href="${pageContext.request.contextPath}/resources/pages/Book/editBook.jsp?id=<%= book.getId() %>" class="btn-edit">Edit</a>
                <a href="${pageContext.request.contextPath}/DeleteBookServlet?id=<%= book.getId() %>" class="btn-delete" onclick="return confirm('Are you sure?');">Delete</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr><td colspan="7">No books found in Oracle database.</td></tr>
        <%      }
        } catch (Exception e) {
        %>
        <tr><td colspan="7" style="color:red;">Error: <%= e.getMessage() %></td></tr>
        <% } %>
        </tbody>
    </table>
</div>

<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/pages/Book/bookFunction.js"></script>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>
