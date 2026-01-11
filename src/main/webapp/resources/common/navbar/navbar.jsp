<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer navUserId = (Integer) session.getAttribute("user_id");
    // Retrieve the admin status from the session
    Boolean isAdmin = (Boolean) session.getAttribute("user_is_admin");

    boolean isLoggedIn = (navUserId != null);
    // Ensure isAdmin is not null before checking value
    boolean showAdminLink = (isAdmin != null && isAdmin);
%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/navbar/navbar.css">
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

        <% if (isLoggedIn && !showAdminLink) { %>
        <a href="${pageContext.request.contextPath}/CustomerServiceServlet?action=viewChat&ownerId=${sessionScope.user_id}" class="support-link">
            <i class="fas fa-headset"></i> Support
        </a>
        <% } %>
    </div>

    <div class="nav-actions">
        <div class="icon-group">

            <%-- New Admin Dashboard Button --%>
            <% if (showAdminLink) { %>
            <a href="${pageContext.request.contextPath}/resources/pages/user/adminDashboard.jsp" class="icon-btn admin-btn" title="Admin Dashboard">
                <i class="fas fa-user-shield"></i>
            </a>
            <% } %>

            <a href="${pageContext.request.contextPath}/resources/pages/cart/Cart.jsp" class="icon-btn cart-btn">
                <i class="fas fa-shopping-cart"></i>
            </a>

            <% if (isLoggedIn) { %>
            <a href="${pageContext.request.contextPath}/resources/pages/user/profile.jsp" class="icon-btn profile-btn" title="My Profile">
                <i class="fas fa-user"></i>
            </a>
            <% } else { %>
            <a href="${pageContext.request.contextPath}/resources/pages/user/login.jsp" class="icon-btn profile-btn" title="Login">
                <i class="fas fa-user"></i>
            </a>
            <% } %>

        </div>
    </div>
</nav>