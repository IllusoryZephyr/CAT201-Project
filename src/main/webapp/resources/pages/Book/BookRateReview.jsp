<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.novelnest.cat201project.models.User" %>
<script src="${pageContext.request.contextPath}/resources/js/bookFunction.js?v=1.1"></script>

<html>
<head>
    <title>Book Review</title>
    <style>
        .book-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .book-table th, .book-table td { padding: 10px; text-align: left; border: 1px solid #ddd; }
        .rating-summary { background: #f9f9f9; padding: 15px; border-radius: 5px; margin: 20px 0; }
        .avg-score { font-weight: bold; color: #f39c12; }
    </style>
    <link rel="stylesheet" type="text/css" href="BookRateReview.css">
    <script src="${pageContext.request.contextPath}/bookFunction.js" defer></script>
</head>
<body>

<p>Debug: Current User ID is <strong>${not empty sessionScope.user ? sessionScope.user.id : 'Not Logged In'}</strong></p>



<div class="review-section">
    <h3>Leave a Review</h3>
    <form id="reviewForm" action="${pageContext.request.contextPath}/submitReview" method="post">
        <input type="hidden" name="bookId" value="2">
        <input type="hidden" id="hiddenUserId" value="1">

        <div class="form-group">
            <label>Rating:</label>
            <select name="rating" required>
                <option value="5">5 - Excellent</option>
                <option value="4">4 - Very Good</option>
                <option value="3">3 - Average</option>
                <option value="2">2 - Poor</option>
                <option value="1">1 - Terrible</option>
            </select>
        </div>

        <div class="form-group">
            <label>Title:</label>
            <input type="text" name="title" placeholder="Summary of your review" required>
        </div>

        <div class="form-group">
            <label>Your Review:</label>
            <textarea name="description" rows="4" required></textarea>
        </div>

        <button type="submit">Submit Review</button>
    </form>
</div>

<hr>

<div class="existing-reviews">
    <h3>Customer Reviews</h3>

    <div id="ratingSummaryContainer" class="rating-summary">
    </div>

    <table class="book-table">
        <thead>
        <tr>
            <th>User ID</th><th>Title</th><th>Comment</th><th>Rating</th><th>Date</th>
        </tr>
        </thead>
        <tbody id="reviewsTableBody">
        </tbody>
    </table>
</div>