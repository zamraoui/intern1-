<?php
session_start();
include 'db.php';  // Include your database connection

// Check if the form was submitted
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // SQL query to check if the user exists
    $sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();
    $user = $result->fetch_assoc();

    // Check if user exists and the password matches (plain text comparison)
    if ($user && $password === $user['password']) {
        // Set session variables
        $_SESSION['user_id'] = $user['user_id'];
        $_SESSION['role'] = $user['role'];

        // Redirect based on the user role
        if ($_SESSION['role'] == 'manager') {
            header("Location: manager_dashboard.php");
        } else {
            header("Location: default_dashboard.php");
        }
        exit();
    } else {
        $error = "Invalid credentials!";
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Aircrew Management</title>
    <!-- Bootstrap for styling -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for eye icon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background: url('https://images.hdqwalls.com/wallpapers/clouds-hd.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            background-color: white blue;
            padding: 50px;
            border-radius: 50px;
            box-shadow: 0 10px 10px;
            text-align: center;
            width: 500px;
            position: relative;
        }

        .login-container img {
            width: 100px;
            margin-bottom: 20px;
        }

        .login-container h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .login-container input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        .login-container .password-wrapper {
            position: relative;
        }

        .login-container .field-icon {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #aaa;
        }

        .login-container button {
            width: 100%;
            padding: 10px;
            background-color: #87CEEB;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .login-container button:hover {
            background-color: #6CA6CD;
        }

        .error {
            color: red;
            margin-bottom: 15px;
        }

        .forgot-password {
            display: block;
            margin-top: 10px;
            color: #87CEEB;
            text-decoration: none;
        }

        .forgot-password:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
    <div class="login-container">
        <img src="images/Your paragraph text.png" alt="Logo">
        <h2>LOGIN</h2>

        <?php if (isset($error)): ?>
            <div class="error"><?php echo $error; ?></div>
        <?php endif; ?>

        <form method="POST" action="">
            <input type="text" name="username" placeholder="Username" required>

            <div class="password-wrapper">
                <input type="password" name="password" id="password" placeholder="Password" required>
                <span toggle="#password" class="fa fa-fw fa-eye field-icon toggle-password"></span>
            </div>

            <button type="submit">FLY</button>
        </form>

        <a href="forgot_password.php" class="forgot-password">Forgot your password?</a>
    </div>

    <script>
        document.querySelector('.toggle-password').addEventListener('click', function () {
            const passwordField = document.querySelector('#password');
            const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordField.setAttribute('type', type);
            this.classList.toggle('fa-eye-slash');
        });
    </script>
</body>

</html>