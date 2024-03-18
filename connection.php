<?php
// Database configuration
$db_host = "localhost";
$db_user = "root";
$db_pass = ""; // Your database password
$db_name = "login_signup"; // database name in XAMPP
$dbport = 4306; //default is usually 3306

// Create a connection to the database
$con = new mysqli($db_host, $db_user, $db_pass, $db_name, $dbport);

// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
?>
