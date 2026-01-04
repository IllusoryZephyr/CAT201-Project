document.addEventListener('DOMContentLoaded', function() {

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

            // Simple Client-side validation
            if (parseFloat(price) <= 0) {
                alert("Price must be greater than 0!");
                event.preventDefault(); // Stop form from submitting
                return;
            }

            if (parseInt(quantity) < 0) {
                alert("Stock cannot be negative!");
                event.preventDefault();
                return;
            }

            // Optional: Final confirmation before updating
            if (!confirm("Save these changes to the database?")) {
                event.preventDefault();
            }
        });
    }
});