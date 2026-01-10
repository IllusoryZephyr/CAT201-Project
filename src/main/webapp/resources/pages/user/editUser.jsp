<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile - NovelNest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages/user/editUser.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="edit-container">
    <h2>Edit Profile</h2>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error-msg"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/UserServlet" method="post" id="editForm">
        <input type="hidden" name="action" value="edit">

        <div class="form-group">
            <label for="user_name">New Username</label>
            <input type="text" id="user_name" name="user_name" required>
        </div>

        <div class="form-group">
            <label for="user_password">New Password</label>
            <input type="password" id="user_password" name="user_password" required onkeyup="checkPassword();">
        </div>

        <div class="form-group">
            <label for="confirm_password">Confirm New Password</label>
            <input type="password" id="confirm_password" name="confirm_password" required onkeyup="checkPassword();">
            <small id="password_status" style="display: block; margin-top: 5px; font-weight: bold;"></small>
        </div>

        <button type="submit" id="submit_btn" class="btn-submit" disabled>Update Profile</button>
    </form>
</div>

<script>
    function checkPassword() {
        const password = document.getElementById('user_password').value;
        const confirm = document.getElementById('confirm_password').value;
        const message = document.getElementById('password_status');
        const btn = document.getElementById('submit_btn');

        // Don't show message if confirm field is empty
        if (confirm.length === 0) {
            message.innerHTML = "";
            btn.disabled = true;
            return;
        }

        if (password === confirm) {
            message.style.color = 'green';
            message.innerHTML = '<i class="fas fa-check-circle"></i> Passwords match';
            btn.disabled = false;
            btn.style.opacity = "1";
            btn.style.cursor = "pointer";
        } else {
            message.style.color = 'red';
            message.innerHTML = '<i class="fas fa-times-circle"></i> Passwords do not match';
            btn.disabled = true;
            btn.style.opacity = "0.5";
            btn.style.cursor = "not-allowed";
        }
    }
</script>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>