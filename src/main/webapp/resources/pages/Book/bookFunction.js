document.addEventListener('DOMContentLoaded', function() {

    // --- BOOK MANAGEMENT LOGIC ---
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const title = this.getAttribute('data-title');
            if (confirm("Are you sure you want to delete '" + title + "'?")) {
                window.location.href = this.getAttribute('href');
            }
        });
    });

    const editForm = document.getElementById('editBookForm');
    if (editForm) {
        editForm.addEventListener('submit', function(event) {
            const price = document.getElementById('price').value;
            const quantity = document.getElementById('quantity').value;

            if (parseFloat(price) <= 0) {
                alert("Price must be greater than 0!");
                event.preventDefault();
                return;
            }

            if (parseInt(quantity) < 0) {
                alert("Stock cannot be negative!");
                event.preventDefault();
                return;
            }

            if (!confirm("Save these changes to the database?")) {
                event.preventDefault();
            }
        });
    }
// --- 1. SETUP VARIABLES ---
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1));
    const urlParams = new URLSearchParams(window.location.search);

    let bookId = urlParams.get('id');

    if (!bookId || isNaN(bookId)) {
        const hiddenIdInput = document.querySelector('input[name="bookId"]');
        if (hiddenIdInput && !isNaN(hiddenIdInput.value)) {
            bookId = hiddenIdInput.value;
        } else {
            bookId = 2;
            console.warn("Book ID was invalid. Defaulting to ID 2 for testing.");
        }
    }

    function fetchSqlRows() {
        fetch(`${contextPath}/submitReview?id=${bookId}&format=html`)
            .then(res => res.text())
            .then(data => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(data, 'text/html');

                const summarySource = doc.querySelector("#sql-summary-data");
                const tableSourceRows = doc.querySelectorAll("#sql-table-data tr");
                console.log("Number of rows found in response:", tableSourceRows.length);
                if (tableSourceRows.length > 0) {
                    console.log("First row content:", tableSourceRows[0].innerHTML);
                }

                const summaryContainer = document.querySelector("#ratingSummaryContainer");
                const tableBody = document.querySelector("#reviewsTableBody");

                if (summarySource && summaryContainer) {
                    summaryContainer.innerHTML = summarySource.innerHTML;
                }

                if (tableBody) {
                    tableBody.innerHTML = ""; // Clear old content
                    if (tableSourceRows.length > 0) {
                        tableSourceRows.forEach(row => {
                            tableBody.appendChild(row);
                        });
                    } else {
                        tableBody.innerHTML = "<tr><td colspan='5'>No reviews yet.</td></tr>";
                    }
                }
            })
            .catch(err => console.error("Fetch Error:", err));
    }

    fetchSqlRows();

    const reviewForm = document.getElementById('reviewForm');
    if (reviewForm) {
        reviewForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new URLSearchParams(new FormData(reviewForm));

            fetch(`${contextPath}/submitReview`, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("Review added!");
                        reviewForm.reset();
                        fetchSqlRows(); // Refresh data without reloading page
                    }
                });
        });
    }
});