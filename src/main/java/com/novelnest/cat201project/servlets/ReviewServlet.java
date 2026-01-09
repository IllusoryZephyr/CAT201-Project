package com.novelnest.cat201project.servlets;

import com.novelnest.cat201project.dao.ReviewsDao;
import com.novelnest.cat201project.models.Reviews;
import com.novelnest.cat201project.models.User;
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


        try {

            //String bookIdStr = request.getParameter("bookId");
            //String userIdStr = request.getParameter("userId");
            String ratingStr = request.getParameter("rating");

            // Safety Check: If any are null or empty, stop and redirect
            /*
            if (bookIdStr == null || bookIdStr.isEmpty() || userIdStr == null || userIdStr.isEmpty()) {
                response.sendRedirect("errorPage.jsp?msg=MissingID");
                return;
            }
             */
            int bookId = 2;
            int userId = 1;

            int rating = Integer.parseInt(ratingStr);
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        // Create and populate the object
        Reviews newReview = new Reviews();
        newReview.setBookId(bookId);
        newReview.setUserId(userId);
        newReview.setRating(rating);
        newReview.setTitle(title);
        newReview.setDescription(description);

        // Save to Database
        boolean success = reviewsDao.addReview(newReview);

            // --- AJAX CHECK ---
            // Check if the request is coming from JavaScript (Fetch/AJAX)
        String requestedWith = request.getHeader("X-Requested-With");

            if ("XMLHttpRequest".equals(requestedWith)) {
                // Send a simple 200 OK status so JS knows it worked
                response.setStatus(HttpServletResponse.SC_OK);
                // Optional: send a message back
                response.getWriter().write("Review saved successfully");
            } else {
                // Standard browser behavior: Redirect back to the review page
                response.sendRedirect("submitReview?id=" + bookId + "&success=" + success);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("submitReview?id=2&error=missing_data");    }

}


}