function validateLoginForm() {
    var userName = document.getElementById('loginUserName').value;
    var password = document.getElementById('loginPassword').value;

    if (userName === "" || password === "") {
        alert("Both username and password are required.");
    } else {
        document.getElementById('loginForm').submit();
    }
}

function toggleLoginPassword() {
    const passwordField = document.getElementById('loginPassword');
    const toggleIcon = document.getElementById('toggleLoginPasswordIcon');

    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        toggleIcon.src = '/images/visible.png'; // تغيير الأيقونة إلى عين مغلقة
    } else {
        passwordField.type = 'password';
        toggleIcon.src = '/images/eye.png'; // تغيير الأيقونة إلى عين مفتوحة
    }
}

function redirectToRegister() {
    window.location.href = "/register";
}