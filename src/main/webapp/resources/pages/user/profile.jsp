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

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="profile-container">
    <h2>User Profile</h2>
    <hr>

    <div class="info-row"><strong>User ID:</strong> ${sessionScope.user_id}</div>
    <div class="info-row"><strong>Username:</strong> ${sessionScope.user_name}</div>
    <div class="info-row"><strong>Member Since:</strong> ${sessionScope.user_creation_date}</div>
    <div class="info-row"><strong>Role:</strong> ${sessionScope.user_is_admin ? "Administrator" : "Customer"}</div>

    <hr>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/resources/pages/user/editUserName.jsp" class="btn btn-edit">Edit User Name</a>
        <a href="${pageContext.request.contextPath}/resources/pages/user/editUserPassword.jsp" class="btn btn-edit">Edit User Password</a>

        <form id="logoutForm" action="${pageContext.request.contextPath}/UserServlet" method="post">
            <input type="hidden" name="action" value="logout">
            <button type="button" class="btn btn-logout" onclick="showModal('logout')">Logout</button>
        </form>

        <form id="deleteForm" action="${pageContext.request.contextPath}/UserServlet" method="POST">
            <input type="hidden" name="action" value="delete">
            <button type="button" class="btn btn-delete" onclick="showModal('delete')">Delete Account</button>
        </form>
    </div>
</div>

<div id="confirmModal" class="modal-overlay">
    <div class="modal-content">
        <h3 id="modalTitle">Confirm Action</h3>
        <p id="modalMessage">Are you sure you want to proceed?</p>
        <div class="modal-buttons">
            <button class="btn btn-cancel" onclick="closeModal()">Cancel</button>
            <button id="confirmActionBtn" class="btn">Confirm</button>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>

<script>
    const modal = document.getElementById('confirmModal');
    const confirmBtn = document.getElementById('confirmActionBtn');

    function showModal(type) {
        const title = document.getElementById('modalTitle');
        const message = document.getElementById('modalMessage');

        if (type === 'logout') {
            title.innerText = "Logout";
            message.innerText = "Are you sure you want to sign out of your account?";
            confirmBtn.className = "btn btn-logout-confirm";
            confirmBtn.onclick = () => document.getElementById('logoutForm').submit();
        } else if (type === 'delete') {
            title.innerText = "Delete Account";
            message.innerText = "This action is permanent. All your data will be lost. Proceed?";
            confirmBtn.className = "btn btn-delete-confirm";
            confirmBtn.onclick = () => document.getElementById('deleteForm').submit();
        }

        modal.classList.add('active');
    }

    function closeModal() {
        modal.classList.remove('active');
    }

    // Close modal if clicking outside the box
    window.onclick = function(event) {
        if (event.target == modal) closeModal();
    }
</script>