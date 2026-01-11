package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.ReviewsDao;
import com.novelnest.cat201project.models.Reviews;
import com.novelnest.cat201project.models.UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpSession;


@WebServlet("/submitReview")
public class ReviewServlet extends HttpServlet {
    private ReviewsDao reviewsDao = new ReviewsDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        HttpSession session = request.getSession();
        if (session.getAttribute("currentUser") == null) {
            //UserInfo testUser = new UserInfo();
            //testUser.setId(43);
            //testUser.setName("TesterAdmin");
            //session.setAttribute("currentUser", testUser);
        }

        if (idStr == null || idStr.isEmpty()) return;

        try {
            int bookId = Integer.parseInt(idStr);
            ReviewsDao reviewDao = new ReviewsDao();
            List<Reviews> reviewList = reviewDao.getReviewsByBookId(bookId);
            double averageRating = reviewDao.getAverageRating(bookId);

            if ("html".equals(request.getParameter("format"))) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();

                // Box 1: Summary
                out.print("<div id='sql-summary-data'>");
                out.print("<strong>" + averageRating + " / 5 Stars</strong>");
                out.print("<p>Based on " + reviewList.size() + " reviews</p>");
                out.print("</div>");

                // Box 2: Table Data (Wrapped in a table to prevent text-mashing)
                out.print("<div id='sql-table-data'><table>");
                for (Reviews r : reviewList) {
                    out.print("<tr>");
                    // Explicitly check if the ID is coming through from the DB
                    out.print("<td>" + (r.getUserId() > 0 ? r.getUserId() : "N/A") + "</td>");
                    out.print("<td>" + r.getTitle() + "</td>");
                    out.print("<td>" + r.getDescription() + "</td>");
                    out.print("<td>" + r.getRating() + "/5</td>");
                    out.print("<td>" + r.getCreateDate() + "</td>");
                    out.print("</tr>");
                }
                out.print("</table></div>");
                return;
            }
            // Standard forward for first load
            request.setAttribute("reviewList", reviewList);
            request.getRequestDispatcher("/resources/pages/Book/BookRateReview.jsp").forward(request, response);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fix for Oracle character encoding
        request.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession(false); // false = don't create a new session if one doesn't exist
            Integer userIdObj = (session != null) ? (Integer) session.getAttribute("user_id") : null;

            int userId;

            if (userIdObj != null) {
                userId = userIdObj;
            } else {
                // 1. Send them to login
                String loginPath = request.getContextPath() + "/resources/pages/user/login.jsp?error=please_login";
                response.sendRedirect(loginPath);
                return;
            }
            String bookIdStr = request.getParameter("bookId");
            String ratingStr = request.getParameter("rating");
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            System.out.println("DEBUG: Received bookId=" + bookIdStr + ", rating=" + ratingStr);

            // Parse Book ID and Rating
            int bookId = 2; // Default fallback
            if (bookIdStr != null && !bookIdStr.trim().isEmpty() && !bookIdStr.equals("null")) {
                bookId = Integer.parseInt(bookIdStr);
            }
            int rating = 5; // Default fallback
            if (ratingStr != null && !ratingStr.trim().isEmpty()) {
                rating = Integer.parseInt(ratingStr);
            }
            // Populate the model
            Reviews newReview = new Reviews();
            newReview.setBookId(bookId);
            newReview.setUserId(userId);
            newReview.setRating(rating);
            newReview.setTitle(title);
            newReview.setDescription(description);

            // Save to Oracle Database
            boolean success = reviewsDao.addReview(newReview);

            // AJAX handling
            String requestedWith = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestedWith)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(success ? "Success" : "Database Error");
            } else {
                // Redirect back to the review page to refresh list
                response.sendRedirect("submitReview?id=" + bookId + "&success=" + success);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("submitReview?id=2&error=invalid_format");
        }
    }


}