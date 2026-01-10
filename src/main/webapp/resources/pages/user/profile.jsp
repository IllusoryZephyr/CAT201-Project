<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect(request.getContextPath() + "/resources/pages/user/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/profile.css">
</head>
<body>

<div class="profile-container">
    <h2>User Profile</h2>
    <hr>

    <div class="info-row"><strong>User ID:</strong> ${sessionScope.user_id}</div>
    <div class="info-row"><strong>Username:</strong> ${sessionScope.user_name}</div>
    <div class="info-row"><strong>Member Since:</strong> ${sessionScope.user_creation_date}</div>
    <div class="info-row"><strong>Role:</strong> ${sessionScope.user_is_admin ? "Administrator" : "Customer"}</div>

    <hr>

    <div class="actions">
        <a href="editUser.jsp" class="btn btn-edit">Edit Profile</a>

        <form action="${pageContext.request.contextPath}/UserServlet" method="post">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="btn btn-logout">Logout</button>
        </form>

        <form action="${pageContext.request.contextPath}/UserServlet" method="POST" onsubmit="return confirm('Are you sure?');">
            <input type="hidden" name="action" value="delete">
            <button type="submit" class="btn btn-delete">Delete Account</button>
        </form>
    </div>
</div>

</body>
</html>