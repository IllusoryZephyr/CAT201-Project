<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Sign Up | NovelNest</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/signup.css">
    <style>
        /* Adding inline styles in case they aren't in your signup.css */
        .hint.invalid { color: #dc3545; font-size: 0.85em; }
        .error-box {
            display: none;
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>

<div class="card">
    <h2 style="text-align: center; margin-top: 0;">Create Account</h2>

    <div id="validation-error" class="error-box"></div>

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

        <div class="form-group">
            <label for="confirm_password">Confirm Password</label>
            <input type="password" id="confirm_password" name="confirm_password" required>
            <div id="matchHint" class="hint"></div>
        </div>

        <button type="submit" class="btn-signup">Register</button>

        <a href="login.jsp" class="login-link">Already have an account? Log in</a>
    </form>
</div>

<script>
    const form = document.getElementById('signupForm');
    const passwordInput = document.getElementById('user_password');
    const confirmInput = document.getElementById('confirm_password');
    const passwordHint = document.getElementById('passwordHint');
    const matchHint = document.getElementById('matchHint');
    const errorBox = document.getElementById('validation-error');

    // 1. Validate Password Length
    passwordInput.addEventListener('input', () => {
        const val = passwordInput.value;
        if (val.length > 0 && val.length < 8) {
            passwordHint.classList.add('invalid');
            passwordHint.textContent = "Too short (" + val.length + "/8 characters)";
        } else {
            passwordHint.classList.remove('invalid');
            passwordHint.textContent = "Must be at least 8 characters";
        }
        checkMatch(); // Re-check match when password changes
    });

    // 2. Validate Password Match in real-time
    const checkMatch = () => {
        if (confirmInput.value.length > 0) {
            if (passwordInput.value !== confirmInput.value) {
                matchHint.textContent = "Passwords do not match";
                matchHint.style.color = "#dc3545";
            } else {
                matchHint.textContent = "Passwords match";
                matchHint.style.color = "#28a745";
                errorBox.style.display = 'none';
            }
        } else {
            matchHint.textContent = "";
        }
    };

    confirmInput.addEventListener('input', checkMatch);

    // 3. Final Validation on Submit
    form.addEventListener('submit', (e) => {
        let message = "";

        if (passwordInput.value.length < 8) {
            message = "Error: Password must be at least 8 characters long.";
        } else if (passwordInput.value !== confirmInput.value) {
            message = "Error: Passwords do not match.";
        }

        if (message !== "") {
            e.preventDefault();
            errorBox.textContent = message;
            errorBox.style.display = 'block';
            window.scrollTo(0, 0); // Scroll to top to see error
        }
    });
</script>

</body>
</html>