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

    <% if (request.getAttribute("error") != null) { %>
    <div class="error-box" style="display: block;">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form id="signupForm" action="${pageContext.request.contextPath}/UserServlet" method="POST">
        <input type="hidden" name="action" value="add">

        <div class="form-group">
            <label for="user_name">Username</label>
            <input type="text" id="user_name" name="user_name" required placeholder="Enter your Username">
        </div>

        <div class="form-group">
            <label for="user_password">Password</label>
            <input type="password" id="user_password" name="user_password"
                   minlength="8" required placeholder="At least 8 characters">
        </div>

        <div class="form-group">
            <label for="confirm_password">Confirm Password</label>
            <input type="password" id="confirm_password" name="confirm_password" required placeholder="Repeat password">
            <span id="match-error" class="validation-message"></span>
        </div>

        <button type="submit" id="submit-btn" class="btn-signup">Register</button>

        <a href="${pageContext.request.contextPath}/resources/pages/user/login.jsp" class="login-link">Already have an account? Log in</a>
    </form>
</div>

<script>
    const password = document.getElementById("user_password");
    const confirm = document.getElementById("confirm_password");
    const errorMsg = document.getElementById("match-error");
    const submitBtn = document.getElementById("submit-btn");

    function validate() {
        if (confirm.value.length > 0) {
            if (password.value === confirm.value) {
                confirm.classList.remove("invalid-input");
                confirm.classList.add("valid-input");
                errorMsg.textContent = "✓ Passwords match";
                errorMsg.className = "validation-message success-text";
                submitBtn.disabled = false;
                submitBtn.style.opacity = "1";
            } else {
                confirm.classList.remove("valid-input");
                confirm.classList.add("invalid-input");
                errorMsg.textContent = "Passwords do not match";
                errorMsg.className = "validation-message error-text";
                submitBtn.disabled = true;
                submitBtn.style.opacity = "0.6";
            }
        } else {
            confirm.classList.remove("invalid-input", "valid-input");
            errorMsg.textContent = "";
        }
    }

    password.addEventListener("input", validate);
    confirm.addEventListener("input", validate);
</script>

</body>
</html>