<?php
session_start();
include("connection.php");
include("functions.php");


// Check if the request is POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $user_name = $_POST['user_name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $confirm_password = $_POST['confirm_password'];

    if (!empty($user_name) && !empty($email) && !empty($password) && !empty($confirm_password)) {
        // Check if user_name is not numeric
        if (!is_numeric($user_name)) {
            // Check if password matches confirm_password
            if ($password === $confirm_password) {
                // Hash the password
                $hashed_password = password_hash($password, PASSWORD_DEFAULT);

                // Insert user data into the database using prepared statement
                $query = "INSERT INTO users (user_name, email, password) VALUES (?, ?, ?)";
                $stmt = $con->prepare($query);
                $stmt->bind_param("sss", $user_name, $email, $hashed_password);
                $stmt->execute();

                // Redirect to login page
                header("Location: login.html");
                exit;
            } else {
                echo "Password and confirm password do not match.";
            }
        } else {
            echo "Username cannot be numeric.";
        }
    } else {
        echo "Please fill in all fields.";
    }
}
?>
