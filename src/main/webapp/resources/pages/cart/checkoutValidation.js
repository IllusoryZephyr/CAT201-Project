function isValidAddress(address) {
    // Allows letters, numbers, spaces, and common symbols: , . ' - / # ( )
    // Must be at least 5 characters long
    const addressPattern = /^[a-zA-Z0-9\s,.'\-\/#()]{5,}$/;
    return addressPattern.test(address);
}

function validateCheckout() {
    var form = document.forms["checkoutForm"];
    var address = form["address"].value;

    if (address == null || address.trim() === "") {
        alert("Please enter a shipping address.");
        form["address"].focus();
        return false;
    }

    if (!isValidAddress(address)) {
        alert("Please enter a valid address (avoid special symbols like @, !, $).");
        form["address"].focus();
        return false;
    }

    var payment = form["paymentMethod"].value;
    if (payment == null || payment === "") {
        alert("Please select a payment method.");
        return false;
    }

    return true;
}