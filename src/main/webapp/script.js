function confirmDelete(bookId) {
    if (confirm("Are you sure you want to delete Book ID: " + bookId + "?")) {
        // Redirect to the Delete Servlet with the ID
        window.location.href = "DeleteBookServlet?id=" + bookId;
    }
}