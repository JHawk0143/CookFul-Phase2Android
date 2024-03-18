// Function to toggle password visibility
document.getElementById('togglePasswordIcon').addEventListener('click', function() {
    var passwordInput = document.getElementById('password');
    var icon = this;
    var text = document.getElementById('togglePasswordText');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
        text.textContent = 'Hide Password';
    } else {
        passwordInput.type = 'password';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
        text.textContent = 'Show Password';
    }
});

// Function to validate form submission
document.getElementById('loginForm').addEventListener('submit', function(event) {
    var usernameInput = document.getElementById('user_name');
    var passwordInput = document.getElementById('password');
    var usernameError = document.getElementById('usernameError');
    var passwordError = document.getElementById('passwordError');
    var loginError = document.getElementById('loginError');

    // Clear any previous error messages
    usernameError.textContent = '';
    passwordError.textContent = '';

    // Check if username and password fields are filled in
    var isValid = true;
    if (usernameInput.value.trim() === '') {
        usernameError.textContent = 'Please enter your username.';
        isValid = false;
    }
    if (passwordInput.value.trim() === '') {
        passwordError.textContent = 'Please enter your password.';
        isValid = false;
    }

    // Password validation (at least 8 characters, including 1 letter, 1 number, and 1 special character)
    var passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/;
    if (!passwordPattern.test(passwordInput.value.trim())) {
        passwordError.textContent = 'Password must be at least 8 characters long and contain at least one letter, one number, and one special character.';
        isValid = false;
    }

    // Prevent form submission if any field is empty or password is invalid
    if (!isValid) {
        event.preventDefault();
    }
});
