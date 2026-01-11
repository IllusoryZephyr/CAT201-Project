<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>NovelNest - Book Catalogue</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/pages/Catalogue/catalogue.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/navbar/navbar.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

<jsp:include page="/resources/common/navbar/navbar.jsp" />

<div class="catalogue-container">
    <!-- Header Section -->
    <div class="catalogue-header">
        <h1>Book Catalogue</h1>
        <p class="subtitle">Discover your next favorite read</p>
    </div>

    <!-- Search and Filter Section -->
    <div class="search-filter-section">
        <form action="${pageContext.request.contextPath}/SearchBookServlet" method="get"
              class="search-form">
            <!-- Search Bar -->
            <div class="search-bar">
                <i class="fas fa-search search-icon"></i>
                <input type="text" name="query" placeholder="Search books by title or author..."
                       value="<%= request.getAttribute(" searchQuery") !=null ?
                                        request.getAttribute("searchQuery") : "" %>"
                       class="search-input"/>
            </div>

            <!-- Category Filter -->
            <div class="filter-group">
                <select name="category" class="category-select">
                    <option value="">All Categories</option>
                    <% String selectedCategory=(String) request.getAttribute("selectedCategory");
                        String[] categories={"Fiction", "Non-Fiction" , "Mystery" , "Romance"
                                , "Science Fiction" , "Fantasy" , "Biography" , "Self-Help" , "History"
                                , "Thriller" }; for (String cat : categories) { boolean
                                isSelected=cat.equals(selectedCategory); %>
                    <option value="<%= cat %>" <%=isSelected ? "selected" : "" %>><%= cat %>
                    </option>
                    <% } %>
                </select>
            </div>

            <!-- Action Buttons -->
            <button type="submit" class="btn-search">
                <i class="fas fa-search"></i> Search
            </button>
            <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/catalogue.jsp"
               class="btn-clear">
                <i class="fas fa-times"></i> Clear
            </a>
        </form>
    </div>

    <!-- Results Count -->
    <% BookDAO dao=new BookDAO(); List<BookInfo> books = (List<BookInfo>)
            request.getAttribute("books");
        if (books == null) {
            books = dao.getBooksForCatalogue();
        }
        int totalBooks = (books != null) ? books.size() : 0;
    %>
    <div class="results-info">
        <p>Showing <strong>
            <%= totalBooks %>
        </strong> book<%= totalBooks !=1 ? "s" : "" %>
        </p>
    </div>

    <!-- Book Grid -->
    <div class="book-grid">
        <% if (books !=null && !books.isEmpty()) { for (BookInfo book : books) { %>
        <div class="book-card">
            <!-- Clickable Image -->
            <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/bookDetails.jsp?id=<%= book.getId() %>"
               class="book-card-link">
                <div class="book-image-container">
                    <% if (book.getImagePath() !=null && !book.getImagePath().isEmpty())
                    { %>
                    <img src="${pageContext.request.contextPath}/<%= book.getImagePath() %>"
                         alt="<%= book.getTitle() %>" class="book-image">
                    <% } else { %>
                    <div class="book-image-placeholder">
                        <i class="fas fa-book"></i>
                    </div>
                    <% } %>
                    <!-- Stock Badge -->
                    <% if (book.getQuantity() <=0) { %>
                    <span class="badge badge-out-of-stock">Out of Stock</span>
                    <% } else if (book.getQuantity() <=5) { %>
                    <span class="badge badge-low-stock">Low Stock</span>
                    <% } %>
                </div>
            </a>

            <!-- Book Info -->
            <div class="book-info">
                <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/bookDetails.jsp?id=<%= book.getId() %>"
                   class="book-title-link">
                    <h3 class="book-title">
                        <%= book.getTitle() %>
                    </h3>
                </a>
                <p class="book-author"><i class="fas fa-user"></i>
                    <%= book.getAuthor() %>
                </p>
                <p class="book-category"><i class="fas fa-tag"></i>
                    <%= book.getCategory() %>
                </p>

                <!-- Price and Actions -->
                <div class="book-footer">
                    <div class="book-price">
                        <span class="price-label">Price:</span>
                        <span class="price-value">RM <%= String.format("%.2f",
                                book.getPrice()) %></span>
                    </div>

                    <% if (book.getQuantity()> 0) { %>
                    <form action="${pageContext.request.contextPath}/CartServlet"
                          method="post" class="add-to-cart-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="bookId"
                               value="<%= book.getId() %>">
                        <input type="hidden" name="title"
                               value="<%= book.getTitle() %>">
                        <input type="hidden" name="price"
                               value="<%= book.getPrice() %>">
                        <input type="hidden" name="image"
                               value="<%= book.getImagePath() %>">
                        <button type="submit" class="btn-add-cart">
                            <i class="fas fa-cart-plus"></i> Add to Cart
                        </button>
                    </form>
                    <% } else { %>
                    <button class="btn-add-cart btn-disabled" disabled>
                        <i class="fas fa-ban"></i> Unavailable
                    </button>
                    <% } %>
                    <form action="${pageContext.request.contextPath}/submitReview" method="get">
                        <input type="hidden" name="id" value="<%= book.getId() %>">
                        <button type="submit" class="btn-review"
                                onclick="<% if (session.getAttribute("user_id") == null) { %> alert('You must log in to write a review!'); return false; <% } %>">>
                            <i class="fas fa-comments"></i> Reviews
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <% } } else { %>
        <div class="no-results">
            <i class="fas fa-search"></i>
            <h3>No books found</h3>
            <p>Try adjusting your search or filter criteria</p>
            <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/catalogue.jsp"
               class="btn-back">
                View All Books
            </a>
        </div>
        <% } %>
    </div>
</div>

<jsp:include page="/resources/common/footer/footer.jsp" />

</body>

</html>