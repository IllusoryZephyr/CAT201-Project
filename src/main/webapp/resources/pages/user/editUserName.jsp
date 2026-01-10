<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User Name</title>
    <link rel="stylesheet" href="editUser.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="container">
    <h2>Change Username</h2>

    <%-- Display error messages if they exist --%>
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/UserServlet" method="POST">
        <%-- Hidden field to tell the Servlet which action to take --%>
        <input type="hidden" name="action" value="edit">

        <div class="form-group">
            <label for="user_name">New Username:</label>
            <input type="text" id="user_name" name="user_name" required>
        </div>

        <button type="submit">Update Name</button>
        <a href="profile.jsp">Cancel</a>
    </form>
</div>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>