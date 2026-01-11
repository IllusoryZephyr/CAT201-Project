<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Customer Service</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages/user/customerService.css">
</head>
<body>

<jsp:include page="../../common/navbar/navbar.jsp" />

<div class="chat-container">
    <div class="chat-header" style="display: flex; justify-content: space-between; align-items: center; padding: 10px 20px;">

        <a href="${sessionScope.user_is_admin ? pageContext.request.contextPath.concat('/CustomerServiceServlet?action=viewAllChats') : pageContext.request.contextPath.concat('/resources/pages/user/profile.jsp')}"
           class="back-btn">
            &larr; ${sessionScope.user_is_admin ? 'Back' : 'Home'}
        </a>

        <div class="header-title">
            <c:choose>
                <c:when test="${sessionScope.user_is_admin}">
                    Viewing Chat with User #${sessionScope.chat_owner_id}
                </c:when>
                <c:otherwise>
                    Customer Support
                </c:otherwise>
            </c:choose>
        </div>

        <div style="width: 80px;"></div>
    </div>

    <div class="message-area" id="messageArea">
        <c:forEach var="msg" items="${chatHistory}">
            <div class="message ${(msg.senderID == msg.ownerID) != (sessionScope.user_id == msg.ownerID) ? 'received' : 'sent'}">
                <c:out value="${msg.message}" />
            </div>
        </c:forEach>
    </div>

    <form class="input-area" action="${pageContext.request.contextPath}/CustomerServiceServlet" method="POST">
        <input type="text" name="message" placeholder="Type your message..." required autocomplete="off">
        <button type="submit">Send</button>
    </form>
</div>

<script>
    // Auto-scroll to bottom of chat
    const messageArea = document.getElementById('messageArea');
    messageArea.scrollTop = messageArea.scrollHeight;
</script>

<jsp:include page="../../common/footer/footer.jsp" />

</body>
</html>