document.addEventListener('DOMContentLoaded', function() {
    // 1. Find all links with the class 'btn-delete'
    const deleteButtons = document.querySelectorAll('.btn-delete');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            // 2. Prevent the link from jumping to the Servlet immediately
            event.preventDefault();

            // 3. Get the title from the 'data-title' attribute
            const title = this.getAttribute('data-title');
            const href = this.getAttribute('href');

            // 4. Show the confirmation
            if (confirm("Are you sure you want to delete '" + title + "'?")) {
                // 5. If they click OK, proceed to the URL
                window.location.href = href;
            }
        });
    });
});