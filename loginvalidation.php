<?php
session_start();
include("connection.php");
include("functions.php");

// Redirect to the search page
header("Location: search.html");
exit;
// Check if the request is POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve username and password from the form
    $user_name = $_POST['user_name'];
    $password = $_POST['password'];

    // Check if username and password fields are not empty
    if (!empty($user_name) && !empty($password)) {
        // Prepare and execute query to retrieve user data based on username
        $query = "SELECT * FROM users WHERE user_name = ? LIMIT 1";
        $stmt = $con->prepare($query);
        $stmt->bind_param("s", $user_name);
        $stmt->execute();
        $result = $stmt->get_result();

        // Check if the user exists
        if ($result->num_rows == 1) {
            // Fetch user data
            $user_data = $result->fetch_assoc();

            // Verify the password with the hashed password stored in the database
            if (password_verify($password, $user_data['password'])) {
                // if password is correct, set session variables and redirect
                $_SESSION['user_id'] = $user_data['user_id'];
                $_SESSION['user_name'] = $user_data['user_name'];

            } else {
                // Incorrect password
                echo "Incorrect password.";
            }
        } else {
            // User does not exist
            echo "User does not exist.";
        }
    } else {
        // Empty username or password fields
        echo "Please enter username and password.";
    }
}
?>
