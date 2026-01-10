<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.Year" %>

<footer class="footer_container">
    <h3>Novel Nest</h3>

    <div class="footer_info">
        <p>Contact us: 014-2794360</p>
        <p>Address: USM, Penang, Malaysia</p>
        <p>Email: novelnest@gmail.com</p>
    </div>

    <p class="footer_copyright">
        &copy; <%= Year.now().getValue() %> NovelNest Bookstore. All rights reserved.
    </p>
</footer>