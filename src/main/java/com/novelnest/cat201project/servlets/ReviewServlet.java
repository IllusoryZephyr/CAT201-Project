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
        if (session.getAttribute("user") == null) {
            UserInfo testUser = new UserInfo();
            testUser.setId(2);
            testUser.setName("TesterAdmin");
            session.setAttribute("user", testUser);
        }

        if (idStr == null || idStr.isEmpty()) return;

        try {
            int bookId = Integer.parseInt(idStr);
            List<Reviews> reviewList = reviewsDao.getReviewsByBookId(bookId);
            double averageRating = reviewsDao.getAverageRating(bookId);

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
            // Get the Book ID from the hidden form field or parameter
            String bookIdStr = request.getParameter("bookId");
            String ratingStr = request.getParameter("rating");
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            // PROJECT REQUIREMENT: Always use User ID 2
            int userId = 2;

            // Parse Book ID and Rating
            int bookId = (bookIdStr != null) ? Integer.parseInt(bookIdStr) : 2; // Default to 2 if null
            int rating = Integer.parseInt(ratingStr);

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