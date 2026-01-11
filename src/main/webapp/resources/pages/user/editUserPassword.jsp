<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Security</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages/user/editUserPassword.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="container">
    <h2>Change Password</h2>

    <form action="${pageContext.request.contextPath}/UserServlet" method="POST" id="passwordForm">
        <input type="hidden" name="action" value="edit">

        <div class="form-group">
            <label for="user_password">New Password</label>
            <input type="password" id="user_password" name="user_password"
                   minlength="8" required placeholder="At least 8 characters">
        </div>

        <div class="form-group">
            <label for="confirm_password">Confirm Password</label>
            <input type="password" id="confirm_password" name="confirm_password" required>
            <span id="match-error" class="validation-message"></span>
        </div>

        <button type="submit" id="submit-btn">Update Password</button>
        <a href="${pageContext.request.contextPath}/resources/pages/user/profile.jsp">Cancel</a>
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

<jsp:include page="../../common/footer/footer.jsp" />
</body>
</html>