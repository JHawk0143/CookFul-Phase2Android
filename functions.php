<?php

function check_login($con)
{
    // Ensure session is started
    session_start();

    // Check if user is logged in
    if(isset($_SESSION['user_id']))
    {
        $id = $_SESSION['user_id'];
        
        // Prepare and bind parameters to avoid SQL injection
        $query = "SELECT * FROM users WHERE user_id = ?";
        $stmt = $con->prepare($query);
        $stmt->bind_param("i", $id);
        $stmt->execute();
        
        // Get result
        $result = $stmt->get_result();
        
        // Check if user exists
        if ($result->num_rows > 0)
        {
            $user_data = $result->fetch_assoc();
            return $user_data;
        }
    }
    
    // Redirect to login page if user is not logged in
    header("Location: loginvalidation.php");
    exit; // Stop script execution
}


function random_number($length) {
    $text = "";
    if($length < 5)
    { 
        $length = 5;
    }

    $len = rand(4,$length);
    
    for ($i = 0; $i < $len; $i++){
        $text .= rand(0,9);
    }
    return $text;
}

?>
