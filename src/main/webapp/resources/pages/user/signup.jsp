<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up | NovelNest</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/signup.css">
</head>
<body>

<div class="card">
    <h2 style="text-align: center; margin-top: 0;">Create Account</h2>

    <div id="validation-error" class="error-box"></div>

    <%-- Display Server Error --%>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error-box" style="display: block;">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form id="signupForm" action="${pageContext.request.contextPath}/UserServlet" method="POST">
        <input type="hidden" name="action" value="add">

        <div class="form-group">
            <label for="user_name">Username</label>
            <input type="text" id="user_name" name="user_name" required>
        </div>

        <div class="form-group">
            <label for="user_password">Password</label>
            <input type="password" id="user_password" name="user_password" required>
            <div id="passwordHint" class="hint">Must be at least 8 characters</div>
        </div>

        <button type="submit" class="btn-signup">Register</button>

        <a href="login.jsp" class="login-link">Already have an account? Log in</a>
    </form>
    </form>
</div>

<script>
    const form = document.getElementById('signupForm');
    const passwordInput = document.getElementById('user_password');
    const passwordHint = document.getElementById('passwordHint');
    const errorBox = document.getElementById('validation-error');

    passwordInput.addEventListener('input', () => {
        const val = passwordInput.value;
        if (val.length > 0 && val.length < 8) {
            passwordHint.classList.add('invalid');
            passwordHint.textContent = "Too short (" + val.length + "/8 characters)";
        } else {
            passwordHint.classList.remove('invalid');
            passwordHint.textContent = "Must be at least 8 characters";
            errorBox.style.display = 'none';
        }
    });

    form.addEventListener('submit', (e) => {
        if (passwordInput.value.length < 8) {
            e.preventDefault();
            errorBox.textContent = "Error: Password must be at least 8 characters long.";
            errorBox.style.display = 'block';
            passwordInput.focus();
        }
    });
</script>

</body>
</html>