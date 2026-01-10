<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer navUserId = (Integer) session.getAttribute("userId");
    boolean isLoggedIn = (navUserId != null);
%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/home.jsp" class="nav-brand">
        <div class="logo-icon">
            <i class="fas fa-book-open"></i>
        </div>
        <span class="brand-text">NovelNest</span>
    </a>

    <div class="nav-links">
        <a href="#">Home</a>
        <a href="#">Featured Books</a>
        <a href="#">Explore Now</a>
        <a href="#">About</a>
        <a href="#">Contact</a>
    </div>

    <div class="nav-actions">
        <div class="icon-group">

            <a href="${pageContext.request.contextPath}/resources/pages/cart/Cart.jsp" class="icon-btn cart-btn">
                <i class="fas fa-shopping-cart"></i>
            </a>

            <% if (isLoggedIn) { %>
            <a href="${pageContext.request.contextPath}/profile.jsp" class="icon-btn profile-btn" title="My Profile">
                <i class="fas fa-user"></i>
            </a>
            <% } else { %>
            <a href="${pageContext.request.contextPath}/login.jsp" class="icon-btn profile-btn" title="Login">
                <i class="fas fa-user"></i>
            </a>
            <% } %>

        </div>
    </div>
</nav>