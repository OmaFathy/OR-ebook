    function addToMyBooks(bookId) {
    const form = document.getElementById(`add-form-${bookId}`);
    const icon = document.getElementById(`add-icon-${bookId}`);
    const url = form.action;

    $.ajax({
    type: 'POST',
    url: url,
    success: function() {
    icon.src = "/images/check.png"; // Change icon to checkmark
    icon.parentElement.onclick = null; // Disable further clicks
},
    error: function() {
    alert('Error adding book');
}
});
}
