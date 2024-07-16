function validateRegisterForm() {
    var userName = document.getElementById('userName').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    if (userName === "" || email === "" || password === "") {
        alert("All fields are required.");
    } else {
        document.getElementById('registerForm').submit();
    }
}

function togglePassword() {
    var passwordField = document.getElementById('password');
    var passwordIcon = document.getElementById('togglePasswordIcon');

    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        passwordIcon.src = '/images/visible.png'; // تغيير الأيقونة إلى عين مغلقة
    } else {
        passwordField.type = 'password';
        passwordIcon.src = '/images/eye.png'; // تغيير الأيقونة إلى عين مفتوحة
    }
}