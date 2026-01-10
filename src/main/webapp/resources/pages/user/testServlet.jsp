<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Servlet Test Dashboard</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .test-section { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; border-radius: 8px; }
        h2 { margin-top: 0; color: #333; }
        label { display: inline-block; width: 100px; }
        input { margin-bottom: 10px; }
    </style>
</head>
<body>

<h1>UserServlet Test Dashboard</h1>

<p>Current ID: ${sessionScope.user_id}</p>
<p>Error: ${requestScope.error}</p>

<div class="test-section">
    <h2>Test: Add User</h2>
    <form action="${pageContext.request.contextPath}/UserServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label>Name:</label>
        <input type="text" name="user_name" required><br>
        <label>Password:</label>
        <input type="password" name="user_password" required><br>
        <button type="submit">Submit Add</button>
    </form>
</div>

<div class="test-section">
    <h2>Test: Edit User</h2>
    <form action="${pageContext.request.contextPath}/UserServlet" method="post">
        <input type="hidden" name="action" value="edit">
        <label>New Name:</label>
        <input type="text" name="user_name" required><br>
        <label>New Pass:</label>
        <input type="password" name="user_password" required><br>
        <button type="submit">Submit Edit</button>
    </form>
</div>

<div class="test-section">
    <h2>Test: Delete User</h2>
    <form action="${pageContext.request.contextPath}/UserServlet" method="post">
        <input type="hidden" name="action" value="delete">
        <button type="submit" style="color: red;">Submit Delete</button>
    </form>
</div>

<div class="test-section">
    <h2>Test: Logout</h2>
    <form action="${pageContext.request.contextPath}/UserServlet" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit" style="color: red;">Logout</button>
    </form>
</div>

<div class="test-section">
    <h2>Test: Login</h2>
    <form action="${pageContext.request.contextPath}/UserServlet" method="post">
        <input type="hidden" name="action" value="authenticate">
        <label>Name:</label>
        <input type="text" name="user_name" required><br>
        <label>Password:</label>
        <input type="password" name="user_password" required><br>
        <button type="submit" style="color: blue;">Login</button>
    </form>
</div>

</body>
</html>