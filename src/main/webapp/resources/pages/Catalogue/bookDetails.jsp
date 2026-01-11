<%@ page import="com.novelnest.cat201project.dao.BookDAO" %>
<%@ page import="com.novelnest.cat201project.dao.ReviewsDao" %>
<%@ page import="com.novelnest.cat201project.models.BookInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>NovelNest - Book Details</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/pages/Catalogue/bookDetails.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/navbar/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/footer/footer.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

<jsp:include page="/resources/common/navbar/navbar.jsp" />

<div class="book-detail-container">
    <% String idParam=request.getParameter("id"); BookInfo book=null; double averageRating=0.0;
    if (idParam !=null && !idParam.isEmpty()) {
        try {
            int bookId=Integer.parseInt(idParam);
            BookDAO dao=new BookDAO();
            ReviewsDao reviewDao=new ReviewsDao();
            book=dao.getBookById(bookId);
            if (book !=null) {
                averageRating=reviewDao.getAverageRating(bookId);
            }
        } catch (Exception e) {
            }
    } %>

    <% if (book !=null) { %>
    <!-- Back Button -->
    <div class="back-nav">
        <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/catalogue.jsp"
           class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Catalogue
        </a>
    </div>

    <!-- Book Details -->
    <div class="book-detail-content">
        <!-- Book Image Section -->
        <div class="book-image-section">
            <% String imgPath=book.getImagePath(); if (imgPath==null ||
                    imgPath.trim().isEmpty()) { imgPath="images/default_book.png" ; } %>
            <img src="${pageContext.request.contextPath}/<%= imgPath %>"
                 alt="<%= book.getTitle() %>" class="book-image"
                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default_book.png';">

            <!-- Stock Badge -->
            <% if (book.getQuantity() <=0) { %>
            <span class="stock-badge out-of-stock"><i
                    class="fas fa-times-circle"></i> Out of Stock</span>
            <% } else if (book.getQuantity() <=5) { %>
            <span class="stock-badge low-stock"><i
                    class="fas fa-exclamation-circle"></i> Low Stock</span>
            <% } else { %>
            <span class="stock-badge in-stock"><i
                    class="fas fa-check-circle"></i> In Stock</span>
            <% } %>
        </div>

        <!-- Book Info Section -->
        <div class="book-info-section">
            <h1 class="book-title">
                <%= book.getTitle() %>
            </h1>

            <div class="book-meta">
                <p class="book-author">
                    <i class="fas fa-user"></i>
                    <span><strong>Author:</strong>
                        <%= book.getAuthor() %>
                    </span>
                </p>
                <p class="book-category">
                    <i class="fas fa-tag"></i>
                    <span><strong>Category:</strong>
                        <%= book.getCategory() %>
                    </span>
                </p>
            </div>

            <!-- Rating Section -->
            <div class="rating-section">
                <div class="rating-stars">
                    <% int fullStars=(int) averageRating; boolean hasHalfStar=(averageRating
                            - fullStars)>= 0.5;
                        int emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
                        for (int i = 0; i < fullStars; i++) { %>
                    <i class="fas fa-star"></i>
                    <% } if (hasHalfStar) { %>
                    <i class="fas fa-star-half-alt"></i>
                    <% } for (int i=0; i < emptyStars; i++) { %>
                    <i class="far fa-star"></i>
                    <% } %>
                </div>
                <span class="rating-value">
                    <%= String.format("%.1f", averageRating) %> out of 5.0
                </span>
            </div>

            <hr class="divider">

            <!-- Synopsis -->
            <div class="synopsis-section">
                <h3><i class="fas fa-book-open"></i> Synopsis</h3>
                <p class="synopsis-text">
                    <%= book.getSynopsis() !=null && !book.getSynopsis().isEmpty() ?
                            book.getSynopsis() : "No synopsis available for this book." %>
                </p>
            </div>

            <hr class="divider">

            <!-- Price and Stock -->
            <div class="price-stock-section">
                <div class="price-display">
                    <span class="price-label">Price</span>
                    <span class="price-value">RM <%= String.format("%.2f", book.getPrice())
                    %></span>
                </div>
                <div class="stock-display">
                    <span class="stock-label">Availability</span>
                    <span class="stock-value">
                        <%= book.getQuantity() %> units in stock
                    </span>
                </div>
            </div>

            <!-- Add to Cart Button -->
            <div class="action-section">
                <% if (book.getQuantity()> 0) { %>
                <form action="${pageContext.request.contextPath}/CartServlet"
                      method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <input type="hidden" name="title" value="<%= book.getTitle() %>">
                    <input type="hidden" name="price" value="<%= book.getPrice() %>">
                    <input type="hidden" name="image"
                           value="<%= book.getImagePath() %>">
                    <button type="submit" class="btn-add-cart">
                        <i class="fas fa-cart-plus"></i> Add to Cart
                    </button>
                </form>
                <% } else { %>
                <button class="btn-add-cart btn-disabled" disabled>
                    <i class="fas fa-ban"></i> Out of Stock
                </button>
                <% } %>
            </div>
        </div>
    </div>

    <% } else { %>
    <!-- Book Not Found -->
    <div class="not-found">
        <i class="fas fa-book-dead"></i>
        <h2>Book Not Found</h2>
        <p>Sorry, we couldn't find the book you're looking for.</p>
        <a href="${pageContext.request.contextPath}/resources/pages/Catalogue/catalogue.jsp"
           class="btn-back">
            <i class="fas fa-arrow-left"></i> Back to Catalogue
        </a>
    </div>
    <% } %>
</div>

<jsp:include page="/resources/common/footer/footer.jsp" />

</body>

</html>