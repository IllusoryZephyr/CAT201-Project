<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customer Support - All Chats</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/pages/user/allMessages.css">
</head>
<body>

<div class="page-wrapper">
    <jsp:include page="../../common/navbar/navbar.jsp" />

    <div class="container">
        <div class="back-nav">
            <a href="${pageContext.request.contextPath}/resources/pages/user/adminDashboard.jsp" class="btn-back">
                <span class="arrow">&larr;</span> Back to Dashboard
            </a>
        </div>

        <h2>Customer Support Inbox</h2>

        <div class="chat-list">
            <c:forEach var="chat" items="${allChats}">
                <c:set var="firstMsg" value="${chat[0]}" />
                <c:set var="lastMsg" value="${chat[chat.size() - 1]}" />

                <a href="${pageContext.request.contextPath}/CustomerServiceServlet?action=viewChat&ownerId=${firstMsg.ownerID}" class="chat-card">
                    <div class="chat-info">
                        <h3>Chat Session: User #${firstMsg.ownerID}</h3>
                        <p class="last-message">
                            <strong>Last:</strong> ${lastMsg.message}
                        </p>
                    </div>
                    <div class="badge">Open Chat</div>
                </a>
            </c:forEach>

            <c:if test="${empty allChats}">
                <div class="chat-card" style="border-left: 5px solid #ccc;">
                    <p>No active chat messages available.</p>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>