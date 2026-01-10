<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Edit User - NovelNest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages/user/adminEditUser.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="edit-container">
    <h2>Update Role</h2>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error-msg"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/UserServlet?action=adminEditUser" method="post" id="editForm">

        <div class="form-group">
            <label for="user_id">User ID</label>
            <input type="text" name="user_id" value="${userToEdit.id}" readonly
               style="background-color: #e9ecef; cursor: not-allowed; color: #6c757d;">
        </div>

        <div class="form-group">
            <label for="user_name">Username</label>
            <input type="text" name="user_name" value="${userToEdit.name}" readonly
                   style="background-color: #e9ecef; cursor: not-allowed; color: #6c757d;">
        </div>

        <input type="hidden" name="user_password" value="${userToEdit.password}">

        <div class="form-group">
            <label for="is_admin">User Role</label>
            <select name="is_admin" id="is_admin">
                <option value="false" ${userToEdit.admin ? '' : 'selected'}>Regular User</option>
                <option value="true" ${userToEdit.admin ? 'selected' : ''}>Administrator</option>
            </select>
        </div>

        <div class="button-row">
            <button type="submit" id="submit_btn" class="btn-submit">Update User</button>
            <a href="${pageContext.request.contextPath}/UserServlet?action=manageUsers" class="btn-cancel">Cancel</a>
        </div>
    </form>
</div>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>