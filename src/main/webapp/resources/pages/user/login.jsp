<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login | NovelNest</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/login.css">
</head>


<body>

<div class="login-card">
    <h2>Login</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error-msg">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/UserServlet" method="POST">
        <input type="hidden" name="action" value="authenticate">

        <div class="form-group">
            <label for="user_name">Username</label>
            <input type="text" id="user_name" name="user_name" required placeholder="Enter your username">
        </div>

        <div class="form-group">
            <label for="user_password">Password</label>
            <input type="password" id="user_password" name="user_password" required placeholder="Enter your password">
        </div>

        <button type="submit" class="btn-login">Sign In</button>
    </form>

    <div class="signup-link">
        <p>Don't have an account?</p>
        <a href="${pageContext.request.contextPath}/resources/pages/user/signup.jsp" class="btn-signup">Create Account</a>
    </div>
</div>

</body>
</html>