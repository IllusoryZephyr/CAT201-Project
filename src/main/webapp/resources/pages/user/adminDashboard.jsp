<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/adminDashboard.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="dashboard-container">
    <header class="dashboard-header">
        <h1>Admin Control Panel</h1>
        <p>Welcome back, Administrator. Manage your platform settings below.</p>
    </header>

    <div class="action-grid">
        <div class="action-card">
            <h3>User Management</h3>
            <p>View, edit, or remove registered users from the database.</p>
            <form action="${pageContext.request.contextPath}/UserServlet" method="GET">
                <input type="hidden" name="action" value="manageUsers">
                <button type="submit" class="btn btn-primary">Manage Users</button>
            </form>
        </div>

        <div class="action-card">
            <h3>Support Chats</h3>
            <p>Monitor and respond to customer service messages and inquiries.</p>
            <form action="${pageContext.request.contextPath}/CustomerServiceServlet" method="GET">
                <input type="hidden" name="action" value="viewAllChats">
                <button type="submit" class="btn btn-primary">View Chats</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>