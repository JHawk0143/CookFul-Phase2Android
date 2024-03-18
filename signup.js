document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('form');
    var username = document.getElementById('user_name');
    var email = document.getElementById('email');
    var password = document.getElementById('password');
    var confirmPassword = document.getElementById('confirm-password');

    username.addEventListener('input', function() {
        if (username.value.trim() && !username.value.includes(' ')) {
            hideError("username-error");
        }
    });

    email.addEventListener('input', function() {
        if (validateEmail(email.value)) {
            hideError("email-error");
        }
    });

    password.addEventListener('input', function() {
        if (validatePassword(password.value)) {
            hideError("password-error");
        }
    });

    confirmPassword.addEventListener('input', function() {
        if (password.value === confirmPassword.value) {
            hideError("confirm-password-error");
        }
    });

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        // Clear previous error messages
        clearErrorMessages();

        var isValid = true;

        // Username Validation
        if (!username.value.trim()|| username.value.includes(' ')) {
            showError("username-error", "Username cannot be blank and cannot contain space.");
            isValid = false;
        }

        // Email Validation
        if (!validateEmail(email.value)) {
            showError("email-error", "Please enter a valid email address.");
            isValid = false;
        }

        // Password Validation
        if (!validatePassword(password.value)) {
            showError("password-error", "Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");
            isValid = false;
        }

        // Confirm Password Validation
        if (password.value !== confirmPassword.value) {
            showError("confirm-password-error", "Passwords do not match.");
            isValid = false;
        }

        // If all validations pass
        if (isValid) {
            
            form.submit();
        }
    });
});

function validateEmail(email) {
    var re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return re.test(String(email).toLowerCase());
}

function validatePassword(password) {
    var re = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    return re.test(String(password));
}

function showError(elementId, message) {
    var element = document.getElementById(elementId);
    element.innerText = message;
    element.style.display = 'block';
}

function hideError(elementId) {
    var element = document.getElementById(elementId);
    element.style.display = 'none';
}

function clearErrorMessages() {
    var errorMessages = document.getElementsByClassName('error-message');
    for (var i = 0; i < errorMessages.length; i++) {
        errorMessages[i].style.display = 'none';
    }
}

function togglePasswordVisibility(passwordFieldId, iconFieldId, textId) {
    var passwordInput = document.getElementById(passwordFieldId);
    var toggleIcon = document.getElementById(iconFieldId);
    var toggleText = document.getElementById(textId);

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        toggleIcon.textContent = '🙈'; // Change to an 'eye closed' icon if available
        toggleText.textContent = "Hide Password";
    } else {
        passwordInput.type = "password";
        toggleIcon.textContent = '👁'; // Change to an 'eye open' icon if available
        toggleText.textContent = "See Password";
    }
}
