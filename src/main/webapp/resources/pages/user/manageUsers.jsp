<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Manage Users - Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/manageUsers.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="admin-container">
    <div class="back-nav">
        <a href="${pageContext.request.contextPath}/resources/pages/user/adminDashboard.jsp" class="btn-back">
            <span class="arrow">&larr;</span> Back to Dashboard
        </a>
    </div>

    <header class="header">
        <h2>User Management System</h2>
    </header>

    <main class="content-box">
        <div class="table-header">
            <h3>Registered Users</h3>
            <p>Click on any row to edit user details.</p>
        </div>

        <table class="user-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Role</th>
                <th>Date Created</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${userList}">
                <tr onclick="window.location.href='${pageContext.request.contextPath}/UserServlet?action=adminEditUserForm&user_id=${user.id}'">
                    <td><strong>#${user.id}</strong></td>
                    <td>${user.name}</td>
                    <td>
                        <span class="role-badge ${user.admin ? 'admin' : 'user'}">
                                ${user.admin ? "Admin" : "User"}
                        </span>
                    </td>
                    <td>${user.created}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </main>
</div>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>